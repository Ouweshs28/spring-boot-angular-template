package com.project.template.dto;

import com.project.template.Price;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUpdateArticleRequest {
    private Long id;
    @Price
    private Double price;
}
