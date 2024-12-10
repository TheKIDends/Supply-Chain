package com.supplychain.productservice.command.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;
import com.supplychain.productservice.command.command.CreateCategoryCommand;
import com.supplychain.productservice.command.model.CategoryRequestModel;
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
public class CategoryCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @PostMapping("${endpoint.add-category}")
    public ResponseEntity<Map<String, Object>> addCategory(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CategoryRequestModel model) {
        String accessToken = authorizationHeader.replace("Bearer ", "");

        Map<String, Object> response = new HashMap<>();

        String userName = null;
        try {
            userName = TokenUtils.getUsernameFromToken(accessToken);
        } catch (JWTVerificationException e) {
            response.put("message", "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        GetDetailsUserQuery getDetailsUserQuery  = new GetDetailsUserQuery(null, userName);

        UserResponseCommonModel userModel =
                queryGateway.query(getDetailsUserQuery, ResponseTypes.instanceOf(UserResponseCommonModel.class)).join();

        if (!Objects.equals(userModel.getRole(), UserRole.ADMIN)) {
            response.put("message", "Chỉ quản trị viên mới có quyền thêm danh mục");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CreateCategoryCommand command =
                new CreateCategoryCommand(UUID.randomUUID().toString(), model.getCategoryName(), Status.ENABLED);
        try {
            commandGateway.sendAndWait(command);
            response.put("message", "Thêm danh mục thành công");
            response.put("data", model);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Có lỗi xảy ra! Vui lòng thử lại");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
