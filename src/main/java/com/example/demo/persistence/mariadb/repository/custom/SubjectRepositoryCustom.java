package com.example.demo.persistence.mariadb.repository.custom;

import com.example.demo.entity.Subject;
import com.example.demo.request.PageCriteria;

import java.util.List;

public interface SubjectRepositoryCustom {

    List<Subject> listSubject(String keyword, PageCriteria pageCriteria);

    long countSubject(String keyword);

}
