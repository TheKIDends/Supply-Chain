package com.supplychain.productservice.command.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Request {
    @Id
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

}
