package com.gnivc.driverservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumFilter, Enum<?>> {

    private String[] values;
    private boolean exclude;

    @Override
    public void initialize(EnumFilter annotation) {
        values = annotation.values();
        exclude = annotation.exclude();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (exclude) {
            return Arrays.stream(values)
                .anyMatch(val -> val.equals(value.name()));
        } else {
            return Arrays.stream(values)
                .noneMatch(val -> val.equals(value.name()));
        }
    }
}