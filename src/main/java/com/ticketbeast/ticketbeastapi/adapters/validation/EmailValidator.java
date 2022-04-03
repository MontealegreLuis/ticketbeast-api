package com.ticketbeast.ticketbeastapi.adapters.validation;

import com.montealegreluis.assertions.Assert;
import io.vavr.control.Try;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class EmailValidator implements ConstraintValidator<Email, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value != null && Try.run(() -> Assert.email(value)).isSuccess()) {
      return true;
    }

    final String errorMessage = context.getDefaultConstraintMessageTemplate();
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    return false;
  }
}
