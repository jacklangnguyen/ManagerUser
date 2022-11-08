package com.example.demo.request;

import org.springframework.data.domain.Pageable;

public interface PageableMapper <T>{
    Pageable toPageable(T criteria);
}
