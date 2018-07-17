package com.cs.controller.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.interactors.kafka.producer.KafkaJsonProducerInteractor;
import com.cs.model.kafka.KafkaMessageModel;

@Controller
public class JsonKafkaProducerController {
  
  @Autowired
  KafkaJsonProducerInteractor KafkaJsonProducerInteractor;
  
  @PostMapping("sendJosnMessage/")
  public @ResponseBody String handleRequest(@RequestBody KafkaMessageModel model)
  {
    
    return KafkaJsonProducerInteractor.sendMessage(model);
  }
  
}
