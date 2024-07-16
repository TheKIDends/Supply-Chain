package org.hyperledger.fabric.supplychain.entity;

import lombok.*;

import org.hyperledger.fabric.contract.annotation.DataType;
import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.Property;

@Getter
@Setter
@ToString
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

    public Request() {
    }

    public Request(String requestId, String senderId, String recipientId, String dateCreated, String dateModified, String requestType, String requestStatus, String entityName) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.entityName = entityName;
    }
}
