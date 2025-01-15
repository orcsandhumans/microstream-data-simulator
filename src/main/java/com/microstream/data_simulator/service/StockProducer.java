package com.microstream.data_simulator.service;

import com.microstream.data.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockProducer {

  private final KafkaTemplate<String, Stock> kafkaTemplate;
  private final String topic;


  public StockProducer(@Value("${kafka.topic.stock.name}") String topic, KafkaTemplate<String, Stock> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
  }

  public void produce(Stock stock) {
    kafkaTemplate.send(this.topic, stock.getName(), stock);
  }
}
