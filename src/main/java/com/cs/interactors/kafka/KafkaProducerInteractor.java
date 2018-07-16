package com.cs.interactors.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cs.model.kafka.KafkaMessageModel;

@Component
public class KafkaProducerInteractor {
  
  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;
  
  @Value("${kafka.topic}")
  private String                topic;
  
  public String sendMessage(KafkaMessageModel messageModel)
  {
    String messageInfo = null;
    
    try {
      System.out.println("************** Data Size : " + messageModel.getKey().getBytes().length + messageModel.getValue().getBytes().length);
      kafkaTemplate.send(topic, messageModel.getKey(), messageModel.getValue()).get();
      messageInfo = "Message Sent Successfully";
    }
    catch (Exception e) {
      messageInfo = "Message Cannot Sent";
      // e.printStackTrace();
    }
    
    return messageInfo;
  }
  
}
