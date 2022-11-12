package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Subject {

    private String id;

    private String subjectId;
    private String subjectName;
}
