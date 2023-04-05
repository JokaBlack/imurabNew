package com.attractorschool.imurab.service;

public interface BaseService<T> {
    T create(T t);

    T saveOrUpdate(T t);

    T delete(T t);

    T findById(Long id);
}