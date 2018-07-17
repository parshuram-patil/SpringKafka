package com.cs.interactors.kafka.listerners;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

public class KafkaMessageListenerInteractor implements AcknowledgingMessageListener<String, String> {

  @Override
  public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment)
  {
    System.out.println("*********** In onMessage() Message Recieved ---> " + data.key() + "::" + data.value() + " ***********");
    acknowledgment.acknowledge();
  }
  
}
