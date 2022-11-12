package com.example.demo.persistence.mariadb.entity;


import com.example.demo.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
public class UserEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String emailId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User toUser() {
        return User.builder()
                .id(Long.toString(this.id))
                .firstName(this.firstName)
                .lastName(this.lastName)
                .emailId(this.emailId)
                .userId(this.userId).build();
    }

}
