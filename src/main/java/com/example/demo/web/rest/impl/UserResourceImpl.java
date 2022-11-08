package com.example.demo.web.rest.impl;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.ServiceResponse;
import com.example.demo.entity.User;
import com.example.demo.request.PageCriteriaRequest;
import com.example.demo.resolver.PageCriteriaValidator;
import com.example.demo.service.UserService;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import com.example.demo.web.rest.UserResource;

import com.example.demo.web.rest.model.DeleteUserList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class UserResourceImpl implements UserResource {


    private final UserService userService;

    @Autowired
    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PageCriteriaValidator
    public ServiceResponse<PageResponse<User>> getUserList(String keyWord, PageCriteriaRequest pageCriteriaRequest) {
        log.info("Rest get User List");
        PageResponse<User> page = userService.findKeywordUser(keyWord, pageCriteriaRequest.getPageCriteria());
        return ServiceResponse.succed(HttpStatus.OK, page);
    }

    @Override
    public ServiceResponse<User> addUser(User user) {
        log.debug("Rest add User");
        User result = userService.saveUser(user);
        return ServiceResponse.succed(HttpStatus.OK, result);
    }

    @Override
    public ServiceResponse<User> updateUser(User user)  {
        log.debug("Rest update User");
        try {
            User result = userService.updateUser(user);
            return ServiceResponse.succed(HttpStatus.OK, result);
        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }

    }

    @Override
    public ServiceResponse<Void> deleteUser(DeleteUserList ids) {
        log.debug("Rest delete User");
        try {
            this.userService.deleteUser(ids.getIds());
            return new ServiceResponse<>();
        } catch ( DataNotFoundException ex ) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        }
    }


}
