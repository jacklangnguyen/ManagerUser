package com.example.demo.web.rest;



import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.ServiceResponse;
import com.example.demo.entity.User;

import com.example.demo.request.PageCriteriaRequest;
import com.example.demo.web.rest.model.DeleteUserList;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;


@RequestMapping("user")
@Tag(name = "userController", description = "manager user")
public interface UserResource {


    @Operation(description = "get all user or follow keyword")
    @GetMapping("getList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "limit", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")})
    ResponseEntity<PageResponse<User>> getUserList(@RequestPart(required = false) String keyWord , @Valid @ApiIgnore PageCriteriaRequest pageCriteriaRequest);


    @Operation(description = "add user")
    @PostMapping("add")
    ResponseEntity<User> addUser(@Valid @RequestBody User user);


    @Operation(description = "update user information")
    @PutMapping("update")
    ServiceResponse<User> updateUser(@Valid @RequestBody User user) ;

    @Operation(description = "delete User")
    @DeleteMapping("delete")
    ResponseEntity<HttpStatus> deleteUser (@Valid @RequestBody DeleteUserList ids);
}
