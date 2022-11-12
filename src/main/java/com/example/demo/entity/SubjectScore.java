package com.example.demo.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectScore {

    private String id;

    private String userName;
    private String userId;
    private String midtermExamScore;
    private String finalExamScore;
    private String subjectName;
    private String subjectId;
    private String semester;
    private String classroomName;
    private String classroomId;
}
