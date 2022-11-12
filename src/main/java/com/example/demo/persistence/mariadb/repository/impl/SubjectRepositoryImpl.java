package com.example.demo.persistence.mariadb.repository.impl;

import com.example.demo.entity.Subject;
import com.example.demo.persistence.mariadb.entity.SubjectEntity;
import com.example.demo.persistence.mariadb.repository.custom.SubjectRepositoryCustom;
import com.example.demo.request.PageCriteria;
import com.example.demo.utils.PageCriteriaPageableMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {

    private final EntityManager entityManager;

    @Autowired
    private PageCriteriaPageableMapper pageableMapper;

    public SubjectRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Subject> listSubject(String keyword, PageCriteria pageCriteria) {
        Pageable pageable = pageableMapper.toPageable(pageCriteria);
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SubjectEntity> subjectCriteriaQuery = cb.createQuery(SubjectEntity.class);
        Root<SubjectEntity> root = subjectCriteriaQuery.from(SubjectEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if(Strings.isNotBlank(keyword)) {
            Predicate subjectName = cb.like(root.get("subjectName"), String.format("%%%s%%", keyword));
            Predicate subjectId = cb.like(root.get("subjectId"), String.format("%%%s%%", keyword));
            predicates.add(cb.or(subjectName, subjectId));
        }

        Sort sort = pageable.getSort();
        if(sort.isSorted()) {
            try{
                subjectCriteriaQuery.orderBy(QueryUtils.toOrders(sort, root, cb));
            } catch (Exception ignored) {
                subjectCriteriaQuery.orderBy(cb.asc(root.get("updatedAt")));
            }
        } else {
            subjectCriteriaQuery.orderBy(cb.asc(root.get("updatedAt")));
        }

        CriteriaQuery<SubjectEntity> select = subjectCriteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        List<SubjectEntity> result = this.entityManager
                .createQuery(select)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return result.stream().map(SubjectEntity::toSubject).collect(Collectors.toList());
    }


    @Override
    public long countSubject(String keyword) {

        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

        Root<SubjectEntity> rootCount = countQuery.from(SubjectEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (Strings.isNotBlank(keyword)) {
            Predicate subjectName = cb.like(rootCount.get("subjectName"), String.format("%%%s%%", keyword));
            Predicate subjectId = cb.like(rootCount.get("subjectId"), String.format("%%%s%%", keyword));
            predicates.add(cb.or(subjectName, subjectId));
        }

        countQuery.select(cb.count(rootCount)).where(predicates.toArray(new Predicate[]{}));

        return this.entityManager.createQuery(countQuery).getSingleResult();
    }
}
