package com.ticketbeast.ticketbeastapi.adapters.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
public @interface Email {
  String message() default "must be an email address";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
