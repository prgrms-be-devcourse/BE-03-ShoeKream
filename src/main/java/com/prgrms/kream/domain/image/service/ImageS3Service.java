package com.prgrms.kream.domain.image.service;

import static com.prgrms.kream.common.mapper.ImageMapper.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.prgrms.kream.common.exception.FileDeleteFailedException;
import com.prgrms.kream.common.exception.FileUploadFailedException;
import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.model.Image;
import com.prgrms.kream.domain.image.repository.ImageRepository;

@Service
public class ImageS3Service implements ImageService {

	private final String bucket;
	private final AmazonS3 amazonS3;
	private final ImageRepository imageRepository;

	public ImageS3Service(
			@Value("${cloud.aws.s3.bucket}") String bucket, AmazonS3 amazonS3, ImageRepository imageRepository) {
		this.bucket = bucket;
		this.amazonS3 = amazonS3;
		this.imageRepository = imageRepository;
	}

	@Override
	public void register(List<MultipartFile> multipartFiles, Long referenceId, DomainType domainType) {
		if (multipartFiles != null && !multipartFiles.isEmpty()) {
			List<Image> images = uploadImages(multipartFiles, referenceId, domainType);
			imageRepository.saveAllBulk(images);
		}
	}

	@Override
	public List<String> getAll(Long referenceId, DomainType domainType) {
		return toImagePathDto(getImageEntities(referenceId, domainType));
	}

	@Override
	public void deleteAllByReference(Long referenceId, DomainType domainType) {
		List<Image> images = getImageEntities(referenceId, domainType);
		images.stream()
				.map(image -> image.getFullPath().substring(56))
				.forEach(this::deleteImage);
		imageRepository.deleteAllByReferenceIdAndDomainType(referenceId, domainType);
	}

	private List<Image> getImageEntities(Long referenceId, DomainType domainType) {
		return imageRepository.findAllByReferenceIdAndDomainType(referenceId, domainType);
	}

	private void deleteImage(String fileName) {
		try {
			amazonS3.deleteObject(bucket, fileName);
		} catch (AmazonServiceException e) {
			throw new FileDeleteFailedException("Failed to delete (S3)");
		}
	}

	private List<Image> uploadImages(List<MultipartFile> multipartFiles, Long referenceId, DomainType domainType) {
		return multipartFiles.stream()
				.map(multipartFile -> uploadImage(multipartFile, referenceId, domainType))
				.collect(Collectors.toList());
	}

	private Image uploadImage(MultipartFile multipartFile, Long referenceId, DomainType domainType) {
		String originalName = multipartFile.getOriginalFilename();
		String uniqueName = createUniqueName(originalName);
		String fullPath = storeAndGetPath(multipartFile, uniqueName);

		return Image.builder()
				.originalName(originalName)
				.fullPath(fullPath)
				.referenceId(referenceId)
				.domainType(domainType)
				.build();
	}

	private String storeAndGetPath(MultipartFile multipartFile, String uniqueName) {
		ObjectMetadata objectMetadata = new ObjectMetadata();

		try {
			objectMetadata.setContentType(multipartFile.getContentType());
			objectMetadata.setContentLength(multipartFile.getInputStream().available());
			amazonS3.putObject(bucket, uniqueName, multipartFile.getInputStream(), objectMetadata);
		} catch (IOException e) {
			throw new FileUploadFailedException("Failed to save (S3)");
		}

		return amazonS3.getUrl(bucket, uniqueName).toString();
	}
}
