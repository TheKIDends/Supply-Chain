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
public class OrderRequest extends Request {
    @Property()
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
