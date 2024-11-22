package com.youfarm.citronix.common.utils;


import com.youfarm.citronix.common.PlantingMonthsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PlantingMonthsValidator.class)
@Target( {ElementType.FIELD , ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlantingMonths {
        String message() default "Planting must be in March, April, or May";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
}
