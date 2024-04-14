package com.rockoon.web.dto.promotion;

import com.rockoon.web.dto.image.ImageRequest;
import com.rockoon.web.dto.option.OptionRequest;
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
    private List<OptionRequest> optionList;
    private List<ImageRequest> imageList;

}
