package com.prgrms.kream.domain.image.service;

import static com.prgrms.kream.common.mapper.ImageMapper.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.common.exception.FileDeleteFailedException;
import com.prgrms.kream.common.exception.FileUploadFailedException;
import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.model.Image;
import com.prgrms.kream.domain.image.repository.ImageRepository;

@Service
@Profile("local")
public class ImageLocalService implements ImageService {

	private final String uploadPath;

	private final ImageRepository imageRepository;

	public ImageLocalService(@Value("${image.path}") String uploadPath, ImageRepository imageRepository) {
		this.uploadPath = uploadPath;
		this.imageRepository = imageRepository;
	}

	@Override
	public void registerImage(List<MultipartFile> multipartFiles, Long referenceId, DomainType domainType) {
		if (multipartFiles != null && !multipartFiles.isEmpty()) {
			List<Image> images = uploadImages(multipartFiles, referenceId, domainType);
			imageRepository.saveAllBulk(images);
		}
	}

	@Override
	public List<String> getAllImages(Long referenceId, DomainType domainType) {
		List<Image> images = imageRepository.findAllByReferenceIdAndDomainType(referenceId, domainType);
		return toImagePathDto(images);
	}

	@Override
	public void deleteAllImagesByReference(Long referenceId, DomainType domainType) {
		List<Image> images = getAllImageEntities(referenceId, domainType);

		if (images.size() != 0) {
			images.stream()
					.map(Image::getFullPath)
					.forEach(this::deleteImage);

			imageRepository.deleteAllByReferenceIdAndDomainType(referenceId, domainType);
		}
	}

	private List<Image> getAllImageEntities(Long referenceId, DomainType domainType) {
		return imageRepository.findAllByReferenceIdAndDomainType(referenceId, domainType);
	}

	private void deleteImage(String fullPath) {
		Path path = Paths.get(fullPath);

		try {
			Files.delete(path);
		} catch (IOException e) {
			throw new FileDeleteFailedException("로컬에서 이미지 삭제를 실패하였습니다.");
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

		storeImage(multipartFile, uniqueName);

		return Image.builder()
				.originalName(originalName)
				.fullPath(getFullPath(uniqueName))
				.referenceId(referenceId)
				.domainType(domainType)
				.build();
	}

	private void storeImage(MultipartFile multipartFile, String uniqueName) {
		try {
			File file = new File(uploadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			multipartFile.transferTo(new File(getFullPath(uniqueName)));
		} catch (IOException e) {
			throw new FileUploadFailedException("로컬에 이미지 업로드를 실패하였습니다.");
		}
	}

	private String getFullPath(String uniqueName) {
		return uploadPath + uniqueName;
	}
}
