package com.bandit.domain.board.dto.promotion;

import com.bandit.domain.music.dto.MusicRequest;
import jakarta.validation.constraints.Min;
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
    @Min(1)
    private int maxAudience;
    @Min(0)
    private int entranceFee;
    private String content;
    private String title;
    private String team;
    private LocalDate showDate;
    private String startTime;
    private String endTime;
    private String showLocation;
    private String bankName;
    private String account;
    private String accountHolder;
    private String refundInfo;
    private List<String> imageList;
    private List<MusicRequest> musicList;

}
