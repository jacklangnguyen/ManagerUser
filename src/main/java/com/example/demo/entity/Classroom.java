package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Classroom {

    private String id;

    private String classroomId;
    private String userNumber;
    private String classroomName;
    private String homeroomTeacher;

    private List<User> userIds = new ArrayList<>();

}
