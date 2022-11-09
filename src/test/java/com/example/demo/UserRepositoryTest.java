package com.example.demo;


import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.repository.UserRepository;
import com.example.demo.persistence.mariadb.repository.custom.UserRepositoryCustom;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EntityScan("com.example.demo.persistence.mariadb.entity")
//@EnableJpaRepositories("com.example.demo")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepositoryCustom userRepositoryCustom;


    @Test
    public void add() {
        User user =  User.builder().firstName("lang ").lastName("huy").emailId("lang@gmail.com").build();
        UserService userService = new UserServiceImpl(userRepositoryCustom, userRepository, em);

        User user1 = userService.saveUser(user);
        Assertions.assertEquals(user.getEmailId(), user1.getEmailId());
        Assertions.assertEquals(user.getFirstName(), user1.getFirstName());
        Assertions.assertEquals(user.getLastName(), user1.getLastName());
    }


}
