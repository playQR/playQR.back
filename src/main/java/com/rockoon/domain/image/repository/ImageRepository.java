package com.rockoon.domain.image.repository;

import com.rockoon.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByBoardId(Long boardId);
}
