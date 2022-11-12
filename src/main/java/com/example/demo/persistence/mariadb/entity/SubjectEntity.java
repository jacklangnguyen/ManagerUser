package com.example.demo.persistence.mariadb.entity;

import com.example.demo.entity.Subject;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class SubjectEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String subjectId;
    private String subjectName;

    public Subject toSubject() {
        return Subject.builder()
                .id(String.valueOf(this.id))
                .subjectId(this.subjectId)
                .subjectName(this.subjectName).build();
    }

}
