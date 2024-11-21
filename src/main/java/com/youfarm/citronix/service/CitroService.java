package com.youfarm.citronix.service;

import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Field;

import java.util.List;
import java.util.Optional;

public interface CitroService<T, ID> {
    T create(T entity) ;
    List<T> getAll();
    Optional<T> getById(ID id);
}
