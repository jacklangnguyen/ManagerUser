package com.example.demo.service;

import com.example.demo.dto.response.PageResponse;
import com.example.demo.entity.User;
import com.example.demo.request.PageCriteria;
import com.example.demo.utils.exception.DataConflictException;
import com.example.demo.utils.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    PageResponse<User> findKeywordUser(String keyWord,PageCriteria pageCriteria);

    User saveUser(User user);

    User updateUser(User user) throws DataNotFoundException, DataConflictException;

    Void deleteUser(List<String> id) throws  DataNotFoundException;
}
