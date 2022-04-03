package com.ticketbeast.ticketbeastapi.adapters.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.montealegreluis.ticketbeast.shared.IntegerValueObject;
import java.io.IOException;

public final class IntegerValueObjectSerializer extends StdSerializer<IntegerValueObject> {
  public IntegerValueObjectSerializer() {
    super(IntegerValueObject.class);
  }

  @Override
  public void serialize(
      IntegerValueObject value, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeNumber(value.value());
  }
}
