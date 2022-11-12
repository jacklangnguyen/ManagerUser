package com.example.demo.persistence.mariadb.repository.impl;

import com.example.demo.entity.SubjectScore;
import com.example.demo.persistence.mariadb.entity.SubjectScoreEntity;
import com.example.demo.persistence.mariadb.repository.custom.SubjectScoreRepositoryCustom;
import com.example.demo.request.PageCriteria;
import com.example.demo.utils.PageCriteriaPageableMapper;
import com.example.demo.utils.SetupPage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SubjectScoreRepositoryImpl implements SubjectScoreRepositoryCustom {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private PageCriteriaPageableMapper pageableMapper;

    @Transactional
    @Override
    public Page<SubjectScore> getClassroomTranscript(String subjectId, String classroomId, PageCriteria pageCriteria) {
        Pageable pageable = pageableMapper.toPageable(pageCriteria);
        StringBuffer sql = new StringBuffer();
        Map<String, Object> params = new HashMap<>();
        List<SubjectScoreEntity> units = new ArrayList<>();
        sql.append(" FROM SubjectScoreEntity S WHERE 1=1");
        if(Strings.isNotBlank(subjectId)) {
            sql.append(" AND S.subjectId like :subjectId ");
            params.put("subjectId", "%" + subjectId + "%" );
        }

        if(Strings.isNotBlank(classroomId)) {
            sql.append(" AND S.classroomId like :classroomId ");
            params.put("classroomId", "%" + classroomId + "%" );
        }

        Query countQuery = entityManager.createQuery("SELECT count(1)" + sql.toString());
        SetupPage.setParams(countQuery, params);

        Number total = (Number) countQuery.getSingleResult();
        if(total.longValue() > 0){
            Query query = entityManager.createQuery(" SELECT S " + sql.toString() + this.addSort(pageCriteria.getSort()), SubjectScoreEntity.class);
            SetupPage.setParamsWithPageable(query, params, pageCriteria, total);
            units = query.getResultList();
        }

        List<SubjectScore> result = units.stream().map(SubjectScoreEntity::toSubjectScore).collect(Collectors.toList());

        return new PageImpl<>(result, pageable, total.longValue());
    }

    public static String addSort(List<String> sort) {

        StringBuilder orderSql = new StringBuilder();
        if(sort == null)
            return "";
        for (String order : sort) {
            orderSql.append("ORDER BY");
            if(order.startsWith("-")) {
                orderSql.append(order.substring(1));
                orderSql.append(" ");
                orderSql.append("DESC");
            } else {
                orderSql.append(order);
                orderSql.append(" ");
                orderSql.append("ASC");
            }
            break;
        }
        return orderSql.toString();
    }
}
