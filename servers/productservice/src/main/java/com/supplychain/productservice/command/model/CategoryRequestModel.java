package com.supplychain.productservice.command.model;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestModel {
    private String categoryName;
    private String categoryStatus;
}
