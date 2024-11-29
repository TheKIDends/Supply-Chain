package com.supplychain.productservice.command.data;

import com.owlike.genson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @JsonProperty("categoryId")
    private String categoryId;

    @JsonProperty("categoryName")
    private String categoryName;

    @JsonProperty("categoryStatus")
    private String categoryStatus;

}
