package com.ticketbeast.ticketbeastapi.mappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.montealegreluis.ticketbeast.values.StringValueObject;
import java.io.IOException;

public final class StringValueObjectSerializer extends StdSerializer<StringValueObject> {
  public StringValueObjectSerializer() {
    super(StringValueObject.class);
  }

  @Override
  public void serialize(
      StringValueObject value, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeString(value.value());
  }
}
