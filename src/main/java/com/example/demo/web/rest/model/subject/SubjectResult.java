package com.example.demo.web.rest.model.subject;

import com.example.demo.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SubjectResult {
    private List<Subject> subjectList;
    private long total;
}
