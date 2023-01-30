package com.prgrms.kream.domain.image.repository;

import java.util.List;

import com.prgrms.kream.domain.image.model.Image;

public interface ImageCustomRepository {

	List<Long> saveAllBulk(List<Image> images);
}
