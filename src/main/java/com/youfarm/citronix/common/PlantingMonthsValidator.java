package com.youfarm.citronix.common;

import com.youfarm.citronix.common.utils.ValidPlantingMonths;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.time.LocalDateTime;

public class PlantingMonthsValidator implements ConstraintValidator<ValidPlantingMonths, LocalDateTime> {


    @Override
    public boolean isValid(LocalDateTime plantingDate, ConstraintValidatorContext constraintValidatorContext) {

        int month = plantingDate.getMonthValue();

        return month >= 3 && month <= 5;
    }
}
