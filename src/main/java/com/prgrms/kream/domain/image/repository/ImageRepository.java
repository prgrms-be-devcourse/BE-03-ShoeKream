package com.prgrms.kream.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.image.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
