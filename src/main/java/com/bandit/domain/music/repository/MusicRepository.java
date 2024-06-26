package com.bandit.domain.music.repository;

import com.bandit.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {

//    List<Music> findMusicsByTitleIn(List<String> titles);
}
