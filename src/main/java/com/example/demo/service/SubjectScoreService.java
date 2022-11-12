package com.example.demo.service;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.Subject;
import com.example.demo.entity.SubjectScore;
import com.example.demo.request.PageCriteria;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;

import java.util.List;

public interface SubjectScoreService {

    List<SubjectScore> getUserTranscript(String userId);

    PageResponse<SubjectScore> getClassroomTranscript(String subjectId, String classromId, PageCriteria pageCriteria);

    SubjectScore addSubjectScore(SubjectScore subject) throws DataConflictException;

    SubjectScore updateSubjectScore(SubjectScore subject) throws DataNotFoundException, DataConflictException;

    Void deleteSubjectScore(List<String> ids) throws DataNotFoundException;
}
