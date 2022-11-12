package com.example.demo.persistence.mariadb.entity;


import com.example.demo.entity.Classroom;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class ClassroomEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String classroomId;
    private long userNumber;
    private String classroomName;
    private String homeroomTeacher;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserEntity> userIds = new ArrayList<>();

    public Classroom toClassroom() {
        return Classroom.builder()
                .id(String.valueOf(this.id))
                .classroomId(this.classroomId)
                .classroomName(this.classroomName)
                .homeroomTeacher(this.homeroomTeacher)
                .userNumber(String.valueOf(userNumber)).build();
    }

}
