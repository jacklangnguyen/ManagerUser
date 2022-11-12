package com.example.demo.persistence.mariadb.repository.custom;

import com.example.demo.entity.SubjectScore;
import com.example.demo.request.PageCriteria;
import org.springframework.data.domain.Page;

public interface SubjectScoreRepositoryCustom {

    Page<SubjectScore> getClassroomTranscript(String Subject, String classroomId, PageCriteria pageCriteria);
}
