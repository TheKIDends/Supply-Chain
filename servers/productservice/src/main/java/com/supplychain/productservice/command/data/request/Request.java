package com.supplychain.productservice.command.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Request {
    @JsonProperty("requestId")
    protected String requestId;

    @JsonProperty("senderId")
    protected String senderId;

    @JsonProperty("recipientId")
    protected String recipientId;

    @JsonProperty("dateCreated")
    protected String dateCreated;

    @JsonProperty("dateModified")
    protected String dateModified;

    @JsonProperty("requestType")
    protected String requestType;

    @JsonProperty("requestStatus")
    protected String requestStatus;

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
