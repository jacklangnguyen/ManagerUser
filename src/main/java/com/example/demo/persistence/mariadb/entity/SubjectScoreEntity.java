package com.example.demo.persistence.mariadb.entity;

import com.example.demo.entity.SubjectScore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class SubjectScoreEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String userName;
    private String userId;
    private Long midtermExamScore;
    private Long finalExamScore;
    private String subjectName;
    private String subjectId;
    private Long semester;
    private String classroomName;
    private String classroomId;

    public SubjectScore toSubjectScore() {
        return SubjectScore.builder()
                .id(String.valueOf(this.id))
                .userId(this.userId)
                .userName(this.userName)
                .midtermExamScore(String.valueOf(this.midtermExamScore))
                .finalExamScore(String.valueOf(this.finalExamScore))
                .subjectId(this.subjectId)
                .subjectName(this.subjectName)
                .semester(String.valueOf(this.semester))
                .classroomId(this.classroomId)
                .classroomName(this.classroomName).build();
    }


}
