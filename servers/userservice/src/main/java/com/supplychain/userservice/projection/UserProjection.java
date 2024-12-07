package com.supplychain.userservice.projection;

import com.supplychain.commonservice.model.BusinessResponseCommonModel;
import com.supplychain.commonservice.model.UserResponseCommonModel;
import com.supplychain.commonservice.query.GetDetailsUserQuery;

import com.supplychain.userservice.data.Business;
import com.supplychain.userservice.repository.BusinessRepository;
import com.supplychain.userservice.repository.UserRepository;

import org.axonframework.queryhandling.QueryHandler;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @QueryHandler
    public UserResponseCommonModel handle(GetDetailsUserQuery getDetailsUserQuery) {
        UserResponseCommonModel model = new BusinessResponseCommonModel();

        Business business = businessRepository.findBusinessById(getDetailsUserQuery.getId());
        BeanUtils.copyProperties(business, model);
//        System.out.println("sđsđá");
//        System.out.println(model.toString());
        return model;  
    }
}
