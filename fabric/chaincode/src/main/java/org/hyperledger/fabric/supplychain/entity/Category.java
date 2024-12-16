package org.hyperledger.fabric.supplychain.entity;

import com.owlike.genson.annotation.JsonProperty;
import lombok.*;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DataType()
public class Category {
    @Property()
    @JsonProperty("categoryId")
    private String categoryId;

    @Property()
    @JsonProperty("categoryName")
    private String categoryName;

    @Property()
    @JsonProperty("categoryStatus")
    private String categoryStatus;

}
