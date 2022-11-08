package com.example.demo.service.impl;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.entity.UserEntity;
import com.example.demo.persistence.mariadb.repository.UserRepository;
import com.example.demo.persistence.mariadb.repository.custom.UserRepositoryCustom;
import com.example.demo.request.PageCriteria;
import com.example.demo.service.UserService;
import com.example.demo.utils.PersistenceHelper;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.internal.util.PersistenceUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryCustom userRepositoryCustom;
    private final UserRepository userRepository;
    private  final EntityManager entityManager;

    @Autowired
    public UserServiceImpl(UserRepositoryCustom userRepositoryCustom, UserRepository userRepository, EntityManager entityManager) {
        this.userRepositoryCustom = userRepositoryCustom;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    public PageResponse<User> findKeywordUser(String keyWord,PageCriteria pageCriteria) {
        log.debug("rest request get list User with Keyword",keyWord);
        Page<User> response = null;
//        if(CollectionUtils.isEmpty(pageCriteria.getSort())) {
//            pageCriteria.setSort(List.of("-updateAt"));
//        }
        response = userRepositoryCustom.findKeywordUser(keyWord, pageCriteria);
        return new PageResponse<>(response.getTotalElements(), response.getContent(),
                pageCriteria.getPage(), pageCriteria.getLimit());
    }

    @Override
    public User saveUser(User user){
        log.debug("rest request add User");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmailId(user.getEmailId());
        UserEntity result = userRepository.save(userEntity);
        return result.toUser();
    }

    @Override
    public User updateUser(User user) throws DataNotFoundException, DataConflictException {
        log.debug("rest request updateUser");
        try {
            UserEntity userEntity = this.entityManager.getReference(UserEntity.class, Long.parseLong(user.getId()));
            userEntity.setEmailId(user.getEmailId());
            userEntity.setLastName(user.getLastName());
            userEntity.setFirstName(user.getFirstName());
            entityManager.flush();
            return userEntity.toUser();
        } catch (EntityNotFoundException ex ) {
            throw new DataNotFoundException("User" + user.getId());
        } catch (PersistenceException px) {
             SQLIntegrityConstraintViolationException ex = PersistenceHelper.findCause(px, SQLIntegrityConstraintViolationException.class);
             if (ex != null) {
                 throw new DataConflictException(user.getEmailId());
             }
             throw px;
        }
    }

    @Override
    @Transactional
    public Void deleteUser(List<String> ids) throws DataNotFoundException {
        for( String id: ids) {
            Optional<UserEntity> userEntity = this.userRepository.findById(Long.parseLong(id));
            if(!userEntity.isPresent()){
                throw new DataNotFoundException("Not found user");
            }
        }
        this.userRepository.deleteAllById(ids.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
        return  null;
    }
}
