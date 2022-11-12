package com.example.demo.persistence.mariadb.repository;

import com.example.demo.persistence.mariadb.entity.SubjectScoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SubjectScoreRepository extends JpaRepository<SubjectScoreEntity, Long> {

    List<SubjectScoreEntity> findAllByUserId(String userId);


}
