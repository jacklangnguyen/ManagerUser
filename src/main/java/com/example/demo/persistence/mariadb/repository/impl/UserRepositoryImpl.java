package com.example.demo.persistence.mariadb.repository.impl;

import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.entity.UserEntity;
import com.example.demo.persistence.mariadb.repository.custom.UserRepositoryCustom;
import com.example.demo.request.PageCriteria;
import com.example.demo.utils.PageCriteriaPageableMapper;
import com.example.demo.utils.SetupPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
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
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Autowired
    private PageCriteriaPageableMapper pageableMapper;

    @Transactional
    @Override
    public Page<User> findKeywordUser(@Nullable String keyword, PageCriteria pageCriteria) {
        Pageable pageable = pageableMapper.toPageable(pageCriteria);
        StringBuffer sql = new StringBuffer();
        Map<String, Object> params = new HashMap<>();
        List<UserEntity>  unit = new ArrayList<>();
        sql.append("FROM UserEntity U WHERE 1 = 1 ");
        if(StringUtils.isNotBlank(keyword)) {
            sql.append(" AND ( U.firstName like :keyword");
            params.put("keyword", "%" + keyword + "%");
            sql.append(" OR U.lastName like :keyword");
            params.put("keyword", "%" + keyword + "%");
            sql.append(" OR U.emailId like :keyword ");
            params.put("keyword", "%" + keyword +  "%");
            sql.append(")");
        }

        Query countQuery = entityManager.createQuery("SELECT count(1)" + sql.toString());
        SetupPage.setParams(countQuery, params);
        Number total = (Number) countQuery.getSingleResult();
        if (total.longValue() > 0) {
            Query query = entityManager.createQuery("SELECT U " + sql.toString() + this.addSort(pageCriteria.getSort()), UserEntity.class);
            SetupPage.setParamsWithPageable( query, params, pageCriteria, total);
            unit = query.getResultList();
        }
        List<User> response = unit.stream().map(UserEntity::toUser).collect(Collectors.toList());
         return new PageImpl<>(response, pageable, total.longValue());
    }

    /**
     * @return
     */

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
