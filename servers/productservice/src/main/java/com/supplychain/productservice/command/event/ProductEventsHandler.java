package com.supplychain.productservice.command.event;

//import com.supplychain.commonservice.query.GetDetailsUserQuery;
import com.supplychain.productservice.command.data.Product;
import com.supplychain.productservice.command.repository.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsHandler {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private transient QueryGateway queryGateway;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        Product product = new Product();
        BeanUtils.copyProperties(event, product);



//        GetDetailsUserQuery getDetailsUserQuery  = new GetDetailsUserQuery(event.getCreatorId());
//
//        BookResponseCommonModel bookResponseModel =
//                queryGateway.query(getDetailsBookQuery,
//                                ResponseTypes.instanceOf(BookResponseCommonModel.class))
//                        .join();
//        if(bookResponseModel.getIsReady() == true) {
//            UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false,event.getEmployeeId(),event.getId());
//            commandGateway.sendAndWait(command);
//        }
//        else {
//
//            throw new Exception("Sach Da co nguoi Muon");
//        }

        productRepository.save(product);
    }
}
