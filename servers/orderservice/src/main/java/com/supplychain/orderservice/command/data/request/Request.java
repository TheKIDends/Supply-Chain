package com.supplychain.orderservice.command.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
