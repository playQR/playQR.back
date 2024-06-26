package com.bandit.domain.showOption.dto;

import com.bandit.domain.showOption.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowOptionRequest {
    private String content;
    private Category category;
}
