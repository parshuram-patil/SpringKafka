package com.cs.interactors.kafka.producer;

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
  
  @Value("${kafka.raw.topic}")
  private String                rawTopic;
  
  public String sendMessage(KafkaMessageModel messageModel, String type)
  {
    String messageInfo = null;
    String topic = null;
    
    if(type != null && type.startsWith("raw")) {
      topic = this.rawTopic;
    }
    else {
      topic = this.topic;
    }
    
    try {
      System.out.println("************** Message Sent : " + messageModel.getKey() + "::" + messageModel.getValue());
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
