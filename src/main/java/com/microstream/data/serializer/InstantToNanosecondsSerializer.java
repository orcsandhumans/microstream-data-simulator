package com.microstream.data.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;

public class InstantToNanosecondsSerializer extends JsonSerializer<Instant> {

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    if (value != null) {
      long nanoseconds = value.getEpochSecond() * 1_000_000_000L + value.getNano();
      gen.writeNumber(nanoseconds);
    }
  }
}
