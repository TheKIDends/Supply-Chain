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
public class OrderRequest extends Request {
    @JsonProperty("details")
    private String details;

    public OrderRequest() {
        super();
        this.requestType = RequestType.ORDER;
        this.entityName = OrderRequest.class.getSimpleName();
    }

    @Builder
    public OrderRequest(String requestId, String senderId, String recipientId, String dateCreated,
                        String dateModified, String requestStatus, String details
    ) {
        super(requestId, senderId, recipientId, dateCreated, dateModified, RequestType.ORDER,
                requestStatus, OrderRequest.class.getSimpleName());
          this.details = details;
    }

}
