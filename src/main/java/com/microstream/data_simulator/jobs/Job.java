package com.microstream.data_simulator.jobs;

import com.microstream.data.generator.StockGenerator;
import com.microstream.data_simulator.service.StockProducer;
import org.springframework.lang.NonNull;

public class Job implements Runnable {

  private final String stock;
  private final StockProducer stockProducer;

  public Job(@NonNull String stock, StockProducer stockProducer) {
    this.stock = stock;
    this.stockProducer = stockProducer;
  }

  @Override
  public void run() {
    StockGenerator stockGenerator = StockGenerator.createDefaultStockGenerator(this.stock);

    while (!Thread.currentThread().isInterrupted()) {
      try {
        stockProducer.produce(stockGenerator.generate());
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
      }
    }
  }
}
