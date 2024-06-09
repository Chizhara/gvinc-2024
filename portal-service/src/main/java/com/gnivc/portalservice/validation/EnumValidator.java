package com.gnivc.portalservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumFilter, Enum<?>> {

    private String[] allowed;

    @Override
    public void initialize(EnumFilter annotation) {
        allowed = annotation.values();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(allowed).anyMatch(val -> val.equals(value.name()));
    }
}