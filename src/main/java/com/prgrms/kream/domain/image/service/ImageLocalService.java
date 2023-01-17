package com.prgrms.kream.domain.image.service;

import static com.prgrms.kream.common.mapper.ImageMapper.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.model.Image;
import com.prgrms.kream.domain.image.repository.ImageRepository;

@Service
public class ImageLocalService implements ImageService {

	private final String uploadPath;

	private final ImageRepository imageRepository;

	public ImageLocalService(@Value("${image.path}") String uploadPath, ImageRepository imageRepository) {
		this.uploadPath = uploadPath;
		this.imageRepository = imageRepository;
	}

	@Override
	public void register(List<MultipartFile> multipartFiles, Long referenceId, DomainType domainType) {
		if (multipartFiles != null && !multipartFiles.isEmpty()) {
			List<Image> images = uploadImages(multipartFiles, referenceId, domainType);
			imageRepository.saveAll(images);
		}
	}

	@Override
	public List<String> getAll(Long referenceId, DomainType domainType) {
		List<Image> images = imageRepository.findAllByReferenceIdAndDomainType(referenceId, domainType);
		return toImagePathDto(images);
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
			File file = new File(getFullPath(uniqueName));
			if (!file.exists()) {
				file.mkdirs();
			}
			multipartFile.transferTo(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getFullPath(String uniqueName) {
		return uploadPath + uniqueName;
	}

	private String createUniqueName(String originalName) {
		String ext = extractExtension(originalName);
		String uuid = UUID.randomUUID().toString();

		return uuid + "." + ext;
	}

	private String extractExtension(String originalName) {
		int pos = originalName.lastIndexOf(".");

		return originalName.substring(pos + 1);
	}
}
