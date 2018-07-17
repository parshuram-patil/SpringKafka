package com.cs.interactors.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cs.model.kafka.KafkaMessageModel;

@Component
public class KafkaJsonProducerInteractor {
  
  @Autowired
  KafkaTemplate<String, KafkaMessageModel> jsonKafkaTemplate;
  
  @Value("${kafka.json.topic}")
  private String                topic;
  
  public String sendMessage(KafkaMessageModel messageModel)
  {
    String messageInfo = null;
    
    try {
      System.out.println("************** Data Sent : " + messageModel.getKey() + "::" + messageModel.getValue());
      jsonKafkaTemplate.send(topic, messageModel.getKey(), messageModel).get();
      messageInfo = "Message Sent Successfully";
    }
    catch (Exception e) {
      messageInfo = "Message Cannot Sent";
      // e.printStackTrace();
    }
    
    return messageInfo;
  }
  
}
