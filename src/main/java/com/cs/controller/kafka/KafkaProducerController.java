package com.cs.controller.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.interactors.kafka.producer.KafkaProducerInteractor;
import com.cs.model.kafka.KafkaMessageModel;

@Controller
public class KafkaProducerController {
  
  @Autowired
  KafkaProducerInteractor kafkaProducerInteractor;
  
  @PostMapping("sendMessage/{type}")
  public @ResponseBody String handleRequest(@RequestBody KafkaMessageModel model, @PathVariable String type)
  {
    
    return kafkaProducerInteractor.sendMessage(model, type);
  }
  
}
