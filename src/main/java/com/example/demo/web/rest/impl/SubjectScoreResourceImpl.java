package com.example.demo.web.rest.impl;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.ServiceResponse;
import com.example.demo.entity.SubjectScore;
import com.example.demo.request.PageCriteriaRequest;
import com.example.demo.resolver.PageCriteriaValidator;
import com.example.demo.service.SubjectScoreService;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import com.example.demo.web.rest.SubjectScoreResource;
import com.example.demo.web.rest.model.DeleteSubjectScoreList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class SubjectScoreResourceImpl implements SubjectScoreResource {

    private final SubjectScoreService subjectScoreService;

    public SubjectScoreResourceImpl(SubjectScoreService subjectScoreService) {
        this.subjectScoreService = subjectScoreService;
    }

    @Override
    public ServiceResponse<List<SubjectScore>> getUserTranscript(String userId) {
        log.debug("Rest get subjects score list of user  ");
        List<SubjectScore> scoreList = subjectScoreService.getUserTranscript(userId);
        return ServiceResponse.succed(HttpStatus.OK, scoreList);
    }

    @Override
    @PageCriteriaValidator
    public ServiceResponse<PageResponse<SubjectScore>> getClassroomTranscript(String subjectId, String classroomId, @Valid PageCriteriaRequest pageCriteriaRequest) {
        log.debug("Rest get subject transcript of a Classroom" );
        PageResponse<SubjectScore> result = subjectScoreService.getClassroomTranscript(subjectId, classroomId, pageCriteriaRequest.getPageCriteria());
        return ServiceResponse.succed(HttpStatus.OK, result);
    }

    @Override
    public ServiceResponse<SubjectScore> addSubjectScore(@Valid SubjectScore subjectScore) {
        log.debug("Rest add Subject");
        try {
            SubjectScore response = subjectScoreService.addSubjectScore(subjectScore);
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }

    @Override
    public ServiceResponse<SubjectScore> updateSubjectScore(@Valid SubjectScore subjectScore) {
        log.debug("Rest update Subject");
        try {
            SubjectScore response = subjectScoreService.updateSubjectScore(subjectScore);
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (
                DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        } catch (
                DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }

    @Override
    public ServiceResponse<HttpStatus> deleteSubjectScore(@Valid DeleteSubjectScoreList ids) {
        log.debug("Rest delete Id List");
        try {
            this.subjectScoreService.deleteSubjectScore(ids.getIds());
            return ServiceResponse.succed(HttpStatus.OK, null);

        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "Data Not Found");// (HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        }
    }

}
