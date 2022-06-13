package com.cs.controller.kafka;

import com.cs.interactors.kafka.KafkaProducerInteractor;
import com.cs.model.kafka.KafkaMessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KafkaProducerController {

    @Autowired
    KafkaProducerInteractor kafkaProducerInteractor;

    @PostMapping("sendMessage/*")
    public @ResponseBody
    String handleRequest(@RequestBody KafkaMessageModel model) {

        kafkaProducerInteractor.sendMessage(model);

        return null;
    }

}
