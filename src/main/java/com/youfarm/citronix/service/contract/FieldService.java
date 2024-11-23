package com.youfarm.citronix.service.contract;

import com.youfarm.citronix.domain.entity.Field;

public interface FieldService {
    Field create(Field field, Long authId);
}
