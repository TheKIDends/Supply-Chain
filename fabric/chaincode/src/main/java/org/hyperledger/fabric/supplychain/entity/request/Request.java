package org.hyperledger.fabric.supplychain.entity.request;

import lombok.*;

import lombok.experimental.SuperBuilder;
import org.hyperledger.fabric.contract.annotation.DataType;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DataType()
public abstract class Request {
    @Property()
    @JsonProperty("requestId")
    protected String requestId;

    @Property()
    @JsonProperty("senderId")
    protected String senderId;

    @Property()
    @JsonProperty("recipientId")
    protected String recipientId;

    @Property()
    @JsonProperty("dateCreated")
    protected String dateCreated;

    @Property()
    @JsonProperty("dateModified")
    protected String dateModified;

    @Property()
    @JsonProperty("requestType")
    protected String requestType;

    @Property()
    @JsonProperty("requestStatus")
    protected String requestStatus;

    @Property()
    @JsonProperty("entityName")
    protected String entityName;
}
