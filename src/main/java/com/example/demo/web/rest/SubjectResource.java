package com.example.demo.web.rest;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.ServiceResponse;
import com.example.demo.entity.Subject;
import com.example.demo.entity.User;
import com.example.demo.request.PageCriteriaRequest;
import com.example.demo.web.rest.model.DeleteSubjectList;
import com.example.demo.web.rest.model.DeleteUserList;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RequestMapping("subject")
@Tag(name = "SubjectResource", description = "subject resource")
public interface SubjectResource {

    @Operation(description = "search subject")
    @GetMapping("getList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "limit", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")})
    ServiceResponse<PageResponse<Subject>> getSubjectList(@RequestParam(required = false) String keyWord , @Valid @ApiIgnore PageCriteriaRequest pageCriteriaRequest);

    @Operation(description = "add subject")
    @PostMapping("add")
    ServiceResponse<Subject> addSubject(@Valid @RequestBody Subject subject);

    @Operation(description = "update subject")
    @PutMapping("update")
    ServiceResponse<Subject> updateSubject(@Valid @RequestBody Subject subject) ;

    @Operation(description = "delete subject")
    @DeleteMapping("delete")
    ServiceResponse<HttpStatus> deleteSubject (@Valid @RequestBody DeleteSubjectList ids);
}
