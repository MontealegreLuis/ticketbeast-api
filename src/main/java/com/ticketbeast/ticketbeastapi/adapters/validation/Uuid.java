package com.ticketbeast.ticketbeastapi.adapters.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {UuidValidator.class})
public @interface Uuid {
  String message() default "must be a UUID";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
