package com.ticketbeast.ticketbeastapi.adapters.validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class UuidValidatorTest {
  @Test
  void it_prevents_null_values() {
    when(context.getDefaultConstraintMessageTemplate()).thenReturn("must be a UUID");
    when(context.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(builder);
    var valid = validator.isValid(null, context);

    assertFalse(valid);
    verify(context, times(1)).disableDefaultConstraintViolation();
    verify(builder, times(1)).addConstraintViolation();
  }

  @Test
  void it_prevents_invalid_UUID_values() {
    when(context.getDefaultConstraintMessageTemplate()).thenReturn("must be a UUID");
    when(context.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(builder);
    var valid = validator.isValid("not a valid UUID", context);

    assertFalse(valid);
    verify(context, times(1)).disableDefaultConstraintViolation();
    verify(builder, times(1)).addConstraintViolation();
  }

  @Test
  void it_accepts_valid_UUID_values() {
    var valid = validator.isValid("d74baa93-bdc6-40ea-9369-e1a4029443f3", context);

    assertTrue(valid);
    verify(context, times(0)).getDefaultConstraintMessageTemplate();
    verify(context, times(0)).buildConstraintViolationWithTemplate(any(String.class));
    verify(context, times(0)).disableDefaultConstraintViolation();
    verify(builder, times(0)).addConstraintViolation();
  }

  @BeforeEach
  void let() {
    builder = mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
    context = mock(ConstraintValidatorContext.class);
    validator = new UuidValidator();
  }

  private UuidValidator validator;
  private ConstraintValidatorContext context;
  private ConstraintValidatorContext.ConstraintViolationBuilder builder;
}
