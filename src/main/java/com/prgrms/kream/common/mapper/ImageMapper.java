package com.prgrms.kream.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.image.model.Image;

public class ImageMapper {

	private ImageMapper() {
	}

	public static List<String> toImagePathDto(List<Image> images) {
		return images.stream()
				.map(Image::getFullPath)
				.collect(Collectors.toList());
	}
}
