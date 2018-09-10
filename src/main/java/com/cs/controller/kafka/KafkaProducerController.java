package com.cs.controller.kafka;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs.interactors.kafka.KafkaProducerInteractor;
import com.cs.model.kafka.KafkaMessageModel;
import com.cs.proto.compile.klassInstance.KlassInstanceProto.KlassInstance;

@Controller
public class KafkaProducerController {
  
  @Autowired
  KafkaProducerInteractor kafkaProducerInteractor;
  
  @PostMapping("sendMessage/*")
  public @ResponseBody String handleRequest(@RequestBody KafkaMessageModel model) throws Exception
  {
	  
   /* KlassInstance.Builder klassInstance = KlassInstance.newBuilder();
    klassInstance.setId("123");*/
	
	  InputStream input = new FileInputStream("src/main/resources/protoTest/klassinstancecache_encoded.txt");
	    
	    KlassInstance klassInstance = KlassInstance.parseFrom(input);
	  
    kafkaProducerInteractor.sendMessage(klassInstance);
    //return kafkaProducerInteractor.sendMessage(model);
    return null;
  }
  
}
