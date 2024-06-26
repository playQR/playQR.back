package com.bandit.domain.image.repository;

import com.bandit.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);
}
