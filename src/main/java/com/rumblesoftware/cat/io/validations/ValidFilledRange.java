package com.rumblesoftware.cat.io.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rumblesoftware.cat.io.validations.impl.ValidFilledRangeValidation;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidFilledRangeValidation.class)
public @interface ValidFilledRange {
	int min() default 1;
	int max() default 100;
    String message() default "{category.patch.input.empty.fields}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
