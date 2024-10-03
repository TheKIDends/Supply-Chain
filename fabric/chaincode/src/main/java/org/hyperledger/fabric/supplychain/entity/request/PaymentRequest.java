package org.hyperledger.fabric.supplychain.entity.request;

import com.owlike.genson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.hyperledger.fabric.supplychain.enumeration.RequestType;

@Getter
@Setter
@ToString
@DataType()
public class PaymentRequest extends Request {
    @Property()
    @JsonProperty("orderId")
    private String orderId;

    @Property()
    @JsonProperty("totalAmount")
    private String totalAmount;

    @Property()
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
