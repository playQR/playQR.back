package com.rockoon.web.dto.option;

import com.rockoon.domain.option.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionRequest {
    private String content;
    private Category category;
}
