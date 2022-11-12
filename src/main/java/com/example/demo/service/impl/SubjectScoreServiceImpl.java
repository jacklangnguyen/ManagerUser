package com.example.demo.service.impl;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.Subject;
import com.example.demo.entity.SubjectScore;
import com.example.demo.persistence.mariadb.entity.SubjectEntity;
import com.example.demo.persistence.mariadb.entity.SubjectScoreEntity;
import com.example.demo.persistence.mariadb.repository.SubjectScoreRepository;
import com.example.demo.persistence.mariadb.repository.custom.SubjectScoreRepositoryCustom;
import com.example.demo.request.PageCriteria;
import com.example.demo.service.SubjectScoreService;
import com.example.demo.utils.PersistenceHelper;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectScoreServiceImpl implements SubjectScoreService {

    private final SubjectScoreRepository subjectScoreRepository;

    private final SubjectScoreRepositoryCustom subjectScoreRepositoryCustom;

    private final EntityManager entityManager;

    public SubjectScoreServiceImpl(SubjectScoreRepository subjectScoreRepository, SubjectScoreRepositoryCustom subjectScoreRepositoryCustom, EntityManager entityManager) {
        this.subjectScoreRepository = subjectScoreRepository;

        this.subjectScoreRepositoryCustom = subjectScoreRepositoryCustom;
        this.entityManager = entityManager;
    }

    @Override
    public List<SubjectScore> getUserTranscript(String userId) {
        log.debug("Request to get the User Transcript");
        List<SubjectScoreEntity> result =  subjectScoreRepository.findAllByUserId(userId);

        return result.stream().map(SubjectScoreEntity::toSubjectScore).collect(Collectors.toList());
    }

    @Override
    public PageResponse<SubjectScore> getClassroomTranscript(String subjectId, String classromId, PageCriteria pageCriteria) {
        Page<SubjectScore> result = subjectScoreRepositoryCustom.getClassroomTranscript(subjectId, classromId, pageCriteria);
        return new PageResponse<>(result.getTotalElements(), result.getContent(), pageCriteria.getPage(), pageCriteria.getLimit()) ;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SubjectScore addSubjectScore(SubjectScore subject) throws DataConflictException {
        log.debug("Rest request add Subject");

        SubjectScoreEntity subjectScoreEntity = new SubjectScoreEntity();

        subjectScoreEntity.setSubjectId(subject.getSubjectId());
        subjectScoreEntity.setSubjectName(subject.getSubjectName());
        try {
            entityManager.persist(subjectScoreEntity);
            entityManager.flush();
            return subjectScoreEntity.toSubjectScore();
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
    public SubjectScore updateSubjectScore(SubjectScore subject) throws DataNotFoundException, DataConflictException {
        log.debug("Rest request update Subject");
        try {
            SubjectScoreEntity subjectScoreEntity = this.entityManager.getReference(SubjectScoreEntity.class, Long.parseLong(subject.getId()));
            subjectScoreEntity.setSubjectId(subject.getSubjectId());
            subjectScoreEntity.setSubjectName(subject.getSubjectName());
            entityManager.flush();
            return subjectScoreEntity.toSubjectScore();
        } catch ( PersistenceException e) {
            SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(e , SQLIntegrityConstraintViolationException.class);
            if(ex != null) {
                throw new DataConflictException(subject.getSubjectId() + subject.getSubjectName());
            }
            throw e;
        }

    }

    @Override
    public Void deleteSubjectScore(List<String> ids) throws DataNotFoundException {
        log.debug("Rest request delete Subject");
        for (String id: ids) {
            Optional<SubjectScoreEntity> subjectEntity = this.subjectScoreRepository.findById(Long.parseLong(id));
            if (!subjectEntity.isPresent()) {
                throw new DataNotFoundException("Id Not Found" + Long.parseLong(id));
            }
        }

        this.subjectScoreRepository.deleteAllById(ids.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));

        return null;
    }

}
