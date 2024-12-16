package com.supplychain.productservice.command.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;
import com.supplychain.productservice.Util.TimeUtils;
import com.supplychain.productservice.command.command.CreateProductCommand;
import com.supplychain.productservice.command.model.ProductRequestModel;
import com.supplychain.productservice.enumeration.Status;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.util.TokenUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}")
public class ProductCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @PostMapping("${endpoint.add-product}")
    public ResponseEntity<Map<String, Object>> addProduct(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProductRequestModel model) {
        String accessToken = authorizationHeader.replace("Bearer ", "");

        Map<String, Object> response = new HashMap<>();

//        String userName = null;
//        try {
//            userName = TokenUtils.getUsernameFromToken(accessToken);
//        } catch (JWTVerificationException e) {
//            response.put("message", "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại");
//            response.put("data", null);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }

        GetDetailsUserQuery getDetailsUserQuery  = new GetDetailsUserQuery(model.getCreatorId(), null);
        UserResponseCommonModel userModel =
                queryGateway.query(getDetailsUserQuery, ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();

        if (userModel == null) {
            response.put("message", "Người dùng không tồn tại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

//        if (!Objects.equals(userModel.getUsername(), userName)) {
//            response.put("message", "Không có quyền truy cập");
//            response.put("data", null);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }

        if (!Objects.equals(userModel.getRole(), UserRole.BUSINESS)) {
            response.put("message", "Chỉ tài khoản doanh nghiệp mới có thể thêm sản phẩm");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CreateProductCommand command =
                new CreateProductCommand(UUID.randomUUID().toString(), model.getProductName(), model.getProductPrice(),
                        model.getCategoryId(), model.getCreatorId(), TimeUtils.getCurrentTimeStrInVietNam(),
                        Status.ENABLED, model.getDetails());
        try {
            commandGateway.sendAndWait(command);

            response.put("message", "Thêm sản phẩm thành công");
            response.put("data", model);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra! Vui lòng thử lại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
}
