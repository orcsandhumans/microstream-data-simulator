package com.microstream.data.generator;

import com.microstream.data.Stock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class StockGenerator {

  private final String stockName;
  private final Instant firstDayClosedAt;
  private final PriceGenerator priceGenerator;

  public StockGenerator(String stockName, PriceGenerator priceGenerator) {
    this.stockName = stockName;
    this.priceGenerator = priceGenerator;
    firstDayClosedAt = getYesterdayClosedAt();
  }

  public static StockGenerator createDefaultStockGenerator(String stock) {
    return new StockGenerator(stock, new GBMPriceGenerator(100.0, 0.20, 0.6));
  }

  public Stock generate() {
    return new Stock(this.stockName, priceGenerator.generate(), Instant.now());
  }

  public Stock generateFirstStock() {
    return new Stock(this.stockName, priceGenerator.generate(), this.firstDayClosedAt);
  }

  public Instant getTodayOpenedAt() {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
    ZonedDateTime today = now
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0);

    return today.toInstant();
  }

  public Instant getTodayCloseAt() {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
    ZonedDateTime today = now
        .withHour(23)
        .withMinute(59)
        .withSecond(59)
        .withNano(0);

    return today.toInstant();
  }

  public Instant getYesterdayClosedAt() {
    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
    ZonedDateTime previousDay = now.minusDays(1)
        .withHour(23)
        .withMinute(59)
        .withSecond(59)
        .withNano(0);

    return previousDay.toInstant();
  }

}
