package com.rumblesoftware.cat.io.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rumblesoftware.cat.io.validations.impl.ValidPatchRequestValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPatchRequestValidator.class)
public @interface ValidPatchRequest {
    String message() default "{category.patch.input.empty.fields}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
	
}
