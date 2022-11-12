package com.example.demo.web.rest;


import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.ServiceResponse;
import com.example.demo.entity.SubjectScore;
import com.example.demo.request.PageCriteriaRequest;
import com.example.demo.web.rest.model.DeleteSubjectScoreList;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("subjectScore")
@Tag(name = "SubjectScoreResource" , description = " Subject Score")
public interface SubjectScoreResource {

    @Operation(description = "get User Transcript")
    @GetMapping("userTranscript")
    ServiceResponse<List<SubjectScore>> getUserTranscript(@RequestPart String userId);

    @Operation(description = "get score of subject in a classroom")
    @GetMapping("classroomTranscript")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "limit", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query")})
    ServiceResponse<PageResponse<SubjectScore>> getClassroomTranscript(@RequestPart String subjectId, @RequestPart String classroomId,
                                                                        @Valid @ApiIgnore PageCriteriaRequest pageCriteriaRequest);

    @Operation(description = "add subject core")
    @PostMapping("add")
    ServiceResponse<SubjectScore> addSubjectScore(@Valid @RequestBody SubjectScore subjectScore);

    @Operation(description = "update subject score")
    @PutMapping("update")
    ServiceResponse<SubjectScore> updateSubjectScore(@Valid @RequestBody SubjectScore subjectScore) ;

    @Operation(description = "delete subject")
    @DeleteMapping("delete")
    ServiceResponse<HttpStatus> deleteSubjectScore(@Valid @RequestBody DeleteSubjectScoreList ids);

}
