package com.bandit.domain.board.dto.promotion;

import com.bandit.domain.music.dto.MusicRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {
    private int maxAudience;
    private String content;
    private String title;
    private String team;
    private LocalDate showDate;
    private String showTime;
    private String showLocation;
    private String bankName;
    private String account;
    private String accountHolder;
    private String refundInfo;
    private List<String> imageList;
    private List<MusicRequest> musicList;

}
