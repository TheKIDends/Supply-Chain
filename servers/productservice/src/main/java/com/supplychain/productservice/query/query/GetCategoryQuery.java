package com.supplychain.productservice.query.query;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryQuery {
    private String categoryId;
}
