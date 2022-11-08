package com.example.demo.service.mapper;

import com.example.demo.entity.User;
import com.example.demo.persistence.mariadb.entity.UserEntity;
import com.example.demo.service.decorator.UserDecorator;
import org.mapstruct.DecoratedWith;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class},
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
        )
@DecoratedWith(UserDecorator.class)
public interface UserMapper extends ModelMapper<UserEntity, User>{

    @Mapping(target = "id", source= "incrementId")
    @Mapping(target = "uuid", expression = "java(UUID.fromString(target.getId))")
    UserEntity toSource(User target);

    @Mapping(target = "incrementId", source = "id")
    @Mapping(target = "id" , expression = "java(source.getUuid().toString)")
    User toTarget(UserEntity source);
}
