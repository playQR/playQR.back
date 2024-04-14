package com.rockoon.domain.music.repository;

import com.rockoon.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
