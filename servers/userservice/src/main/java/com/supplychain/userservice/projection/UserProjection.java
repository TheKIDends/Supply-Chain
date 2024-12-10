package com.supplychain.userservice.projection;

import com.supplychain.commonservice.model.*;
import com.supplychain.commonservice.query.GetDetailsUserQuery;

import com.supplychain.userservice.data.*;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.repository.*;

import org.axonframework.queryhandling.QueryHandler;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserProjection {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @QueryHandler
    public UserResponseCommonModel handle(GetDetailsUserQuery getDetailsUserQuery) {
        UserResponseCommonModel model = null;

        String userId = getDetailsUserQuery.getId();
        String userName = getDetailsUserQuery.getUserName();

        if (model == null && getDetailsUserQuery.getId() != null) {
            User user = userRepository.findUserById(userId);

            if (Objects.equals(user.getRole(), UserRole.ADMIN)) {
                model = new AdminResponseCommonModel();
                Admin admin = adminRepository.findAdminById(userId);
                BeanUtils.copyProperties(admin, model);
            } else
            if (Objects.equals(user.getRole(), UserRole.BUSINESS)) {
                model = new BusinessResponseCommonModel();
                Business business = businessRepository.findBusinessById(userId);
                BeanUtils.copyProperties(business, model);
            } else
            if (Objects.equals(user.getRole(), UserRole.CARRIER)) {
                model = new CarrierResponseCommonModel();
                Carrier carrier = carrierRepository.findCarrierById(userId);
                BeanUtils.copyProperties(carrier, model);
            } else
            if (Objects.equals(user.getRole(), UserRole.CUSTOMER)) {
                model = new CustomerResponseCommonModel();
                Customer customer = customerRepository.findCustomerById(userId);
                BeanUtils.copyProperties(customer, model);
            }
        }

        if (model == null && getDetailsUserQuery.getUserName() != null) {
            User user = userRepository.findByUsername(userName);

            if (Objects.equals(user.getRole(), UserRole.ADMIN)) {
                model = new AdminResponseCommonModel();
                Admin admin = adminRepository.findByUsername(userName);
                BeanUtils.copyProperties(admin, model);
            } else
            if (Objects.equals(user.getRole(), UserRole.BUSINESS)) {
                model = new BusinessResponseCommonModel();
                Business business = businessRepository.findByUsername(userName);
                BeanUtils.copyProperties(business, model);
            } else
            if (Objects.equals(user.getRole(), UserRole.CARRIER)) {
                model = new CarrierResponseCommonModel();
                Carrier carrier = carrierRepository.findByUsername(userName);
                BeanUtils.copyProperties(carrier, model);
            } else
            if (Objects.equals(user.getRole(), UserRole.CUSTOMER)) {
                model = new CustomerResponseCommonModel();
                Customer customer = customerRepository.findByUsername(userName);
                BeanUtils.copyProperties(customer, model);
            }
        }

        return model;
    }
}
