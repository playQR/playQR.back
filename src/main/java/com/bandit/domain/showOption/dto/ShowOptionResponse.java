package com.bandit.domain.showOption.dto;

import com.bandit.domain.showOption.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowOptionResponse {
    private String content;
    private Category category;
}
