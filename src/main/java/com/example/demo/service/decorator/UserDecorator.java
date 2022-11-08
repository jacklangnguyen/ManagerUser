package com.example.demo.service.decorator;

import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.entity.UserEntity;
import com.example.demo.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UserDecorator implements UserMapper {

    @Autowired
    private UserMapper mapper;

    @Override
    public User toTarget(UserEntity source) {

        User user = mapper.toTarget(source);
        return user;

    }

    @Override
    public List<User> toTarget(List<UserEntity> sources) {
        if (sources == null) {
            return new ArrayList<User>();
        }
        return sources.stream().map(this::toTarget).collect(Collectors.toList());
    }
}
