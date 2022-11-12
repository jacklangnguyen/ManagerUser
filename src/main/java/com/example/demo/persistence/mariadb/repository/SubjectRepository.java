package com.example.demo.persistence.mariadb.repository;

import com.example.demo.persistence.mariadb.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

}
