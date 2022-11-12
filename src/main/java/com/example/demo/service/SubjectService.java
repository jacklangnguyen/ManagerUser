package com.example.demo.service;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.Subject;
import com.example.demo.request.PageCriteria;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;

import java.util.List;

public interface SubjectService {

    PageResponse<Subject> getSubjectList(String keyword, PageCriteria pageCriteria);

    Subject addSubject(Subject subject) throws DataConflictException;

    Subject updateSubject(Subject subject) throws DataNotFoundException, DataConflictException;

    Void deleteSubject(List<String> ids) throws DataNotFoundException;

}
