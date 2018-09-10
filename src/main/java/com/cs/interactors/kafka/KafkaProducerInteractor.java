package com.cs.interactors.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cs.model.kafka.KafkaMessageModel;
import com.cs.proto.compile.klassInstance.KlassInstanceProto.KlassInstance;

@Component
public class KafkaProducerInteractor {
  
  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;
  
  @Autowired
  KafkaTemplate<String, KlassInstance> kafkaTemplateForKlassInstance;
  
  @Value("${kafka.topic}")
  private String                topic;
  
  @Value("${kafka.proto.topic}")
  private String                protoTopic;
  
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
  
  public String sendMessage(KlassInstance messageModel)
  {
    String messageInfo = null;
    
    try {
      System.out.println("************** " + messageModel.getId() + " Data Size : " + messageModel.getSerializedSize());
      kafkaTemplateForKlassInstance.send(protoTopic, messageModel.getId(), messageModel).get();
      messageInfo = "Message Sent Successfully";
    }
    catch (Exception e) {
      messageInfo = "Message Cannot Sent";
      // e.printStackTrace();
    }
    
    System.out.println(messageInfo);
    
    return messageInfo;
  }
  
}
