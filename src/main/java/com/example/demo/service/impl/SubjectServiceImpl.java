package com.example.demo.service.impl;


import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.Subject;
import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.entity.SubjectEntity;
import com.example.demo.persistence.mariadb.repository.SubjectRepository;
import com.example.demo.persistence.mariadb.repository.custom.SubjectRepositoryCustom;
import com.example.demo.request.PageCriteria;
import com.example.demo.service.SubjectService;
import com.example.demo.utils.PersistenceHelper;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import com.example.demo.web.rest.model.subject.SubjectResult;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepositoryCustom subjectRepositoryCustom;
    private final SubjectRepository subjectRepository;
    private  final EntityManager entityManager;

    public SubjectServiceImpl(SubjectRepositoryCustom subjectRepositoryCustom, SubjectRepository subjectRepository, EntityManager entityManager) {
        this.subjectRepositoryCustom = subjectRepositoryCustom;
        this.subjectRepository = subjectRepository;
        this.entityManager = entityManager;
    }

    @Override
    public PageResponse<Subject> getSubjectList(String keyword, PageCriteria pageCriteria) {
        log.debug("Rest request get Subject List");
        Page<Subject> response = null;
//        if(CollectionUtils.isEmpty(pageCriteria.getSort())) {
//            pageCriteria.setSort(List.of("-updateAt"));
////        }
//        response = subjectRepositoryCustom.getSubjectList(keyword, pageCriteria);
//        return new PageResponse<>(response.getTotalElements(), response.getContent(),
//                pageCriteria.getPage(), pageCriteria.getLimit());

        SubjectResult subjectResult = new SubjectResult(this.subjectRepositoryCustom.listSubject(keyword, pageCriteria),
                                                        this.subjectRepositoryCustom.countSubject(keyword));

        return new PageResponse<>(subjectResult.getTotal(), subjectResult.getSubjectList(),
                                    pageCriteria.getPage(), pageCriteria.getLimit());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Subject addSubject(Subject subject) throws DataConflictException {
        log.debug("Rest request add Subject");

        SubjectEntity  subjectEntity = new SubjectEntity();

        subjectEntity.setSubjectId(subject.getSubjectId());
        subjectEntity.setSubjectName(subject.getSubjectName());
        try {
            entityManager.persist(subjectEntity);
            entityManager.flush();
            return subjectEntity.toSubject();
        } catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(subject.getSubjectId() + subject.getSubjectName());
            }
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Subject updateSubject(Subject subject) throws DataNotFoundException, DataConflictException {
        log.debug("Rest request update Subject");
        try {
            SubjectEntity subjectEntity = this.entityManager.getReference(SubjectEntity.class, Long.parseLong(subject.getId()));
            subjectEntity.setSubjectId(subject.getSubjectId());
            subjectEntity.setSubjectName(subject.getSubjectName());
            entityManager.flush();
            return subjectEntity.toSubject();
        } catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(subject.getSubjectId() + subject.getSubjectName());
            }
            throw e;
        }

    }

    @Override
    public Void deleteSubject(List<String> ids) throws DataNotFoundException {
        log.debug("Rest request delete Subject");
        for (String id: ids) {
            Optional<SubjectEntity> subjectEntity = this.subjectRepository.findById(Long.parseLong(id));
            if (!subjectEntity.isPresent()) {
                throw new DataNotFoundException("Id Not Found" + Long.parseLong(id));
            }
        }

        this.subjectRepository.deleteAllById(ids.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));

        return null;
    }
}
