package com.rockoon.domain.board.dto.promotion;

import com.rockoon.domain.image.dto.ImageRequest;
import com.rockoon.domain.music.dto.MusicRequest;
import com.rockoon.domain.showOption.dto.OptionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<OptionRequest> optionList;
    private List<ImageRequest> imageList;
    private List<MusicRequest> musicList;

}
