package com.rockoon.domain.showOption.dto;

import com.rockoon.domain.showOption.entity.Category;
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
