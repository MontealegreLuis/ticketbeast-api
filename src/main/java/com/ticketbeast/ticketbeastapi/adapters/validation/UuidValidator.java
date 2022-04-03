package com.ticketbeast.ticketbeastapi.adapters.validation;

import com.montealegreluis.assertions.Assert;
import io.vavr.control.Try;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class UuidValidator implements ConstraintValidator<Uuid, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value != null && Try.run(() -> Assert.uuid(value)).isSuccess()) {
      return true;
    }

    final String errorMessage = context.getDefaultConstraintMessageTemplate();
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
    return false;
  }
}
