package com.microstream.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.microstream.data.serializer.InstantToNanosecondsSerializer;
import java.time.Instant;

public class Stock {
  private final String name;
  private final double price;
  @JsonSerialize(using = InstantToNanosecondsSerializer.class)
  private final Instant time;

  public Stock(String name, double price, Instant time) {
    this.name = name;
    this.price = price;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public Instant getTime() {
    return time;
  }

  @Override
  public String toString() {
    return "Stock{" +
        "name='" + name + '\'' +
        ", price=" + price +
        ", time=" + time +
        '}';
  }
}
