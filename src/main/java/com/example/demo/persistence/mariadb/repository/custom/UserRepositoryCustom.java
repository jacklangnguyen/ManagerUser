package com.example.demo.persistence.mariadb.repository.custom;

import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.entity.UserEntity;
import com.example.demo.request.PageCriteria;
import org.springframework.data.domain.Page;

public interface UserRepositoryCustom {
    Page<User> findKeywordUser(String keyword, PageCriteria pageCriteria);
}
