package com.example.demo.web.rest.impl;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.dto.response.ServiceResponse;
import com.example.demo.entity.Subject;
import com.example.demo.request.PageCriteriaRequest;
import com.example.demo.service.SubjectService;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import com.example.demo.web.rest.SubjectResource;
import com.example.demo.web.rest.model.DeleteSubjectList;
import com.example.demo.web.rest.model.DeleteUserList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class SubjectResourceImpl implements SubjectResource {

    private final SubjectService subjectService;

    public SubjectResourceImpl(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public ServiceResponse<PageResponse<Subject>> getSubjectList(String keyWord, @Valid PageCriteriaRequest pageCriteriaRequest) {
        log.debug("Rest get Subject List");
        PageResponse<Subject> response = subjectService.getSubjectList(keyWord, pageCriteriaRequest.getPageCriteria());
        return ServiceResponse.succed(HttpStatus.OK, response);
    }

    @Override
    public ServiceResponse<Subject> addSubject(@Valid Subject subject) {
        log.debug("Rest add Subject");
        try {
            Subject response = subjectService.addSubject(subject);
            return ServiceResponse.succed(HttpStatus.OK, response);
        } catch (DataConflictException cx) {
            return ServiceResponse.error(HttpStatus.CONFLICT, "DATA CONFLICT");
        }
    }

    @Override
    public ServiceResponse<Subject> updateSubject(@Valid Subject subject) {
        log.debug("Rest update Subject");
        try {
            Subject response = subjectService.updateSubject(subject);
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
    public ServiceResponse<HttpStatus> deleteSubject(@Valid DeleteSubjectList ids) {
        log.debug("Rest delete Id List");
        try {
            this.subjectService.deleteSubject(ids.getIds());
            return ServiceResponse.succed(HttpStatus.OK, null);

        } catch (DataNotFoundException ex) {
            return ServiceResponse.error(HttpStatus.NOT_FOUND, "Data Not Found");// (HttpStatus.NOT_FOUND, "DATA NOT FOUND");
        }
    }
}
