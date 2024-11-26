package com.supplychain.productservice.query.model;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseModel {
    private String categoryId;
    private String categoryName;
}
