package com.blockchain.supplychain.entity.request;

import com.blockchain.supplychain.enumeration.RequestType;
import com.owlike.genson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentRequest extends Request {
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("totalAmount")
    private String totalAmount;

    @JsonProperty("details")
    private String details;

    public PaymentRequest() {
        super();
        this.requestType = RequestType.ORDER;
        this.entityName = OrderRequest.class.getSimpleName();
    }

    @Builder
    public PaymentRequest(String requestId, String senderId, String recipientId, String dateCreated,
                          String dateModified, String requestStatus, String orderId, String totalAmount, String details
    ) {
        super(requestId, senderId, recipientId, dateCreated, dateModified, RequestType.ORDER,
                requestStatus, OrderRequest.class.getSimpleName());
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.details = details;
    }

}
