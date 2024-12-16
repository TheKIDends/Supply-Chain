package com.supplychain.productservice.command.event;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryCreatedEvent {
    private String categoryId;
    private String categoryName;
    private String categoryStatus;
    private String creatorId;
}
