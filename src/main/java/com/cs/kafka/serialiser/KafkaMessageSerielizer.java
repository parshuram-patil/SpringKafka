package com.cs.kafka.serialiser;

import com.cs.model.kafka.KafkaMessageModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class KafkaMessageSerielizer implements Serializer<KafkaMessageModel>, Deserializer<KafkaMessageModel> {

    @Override
    public KafkaMessageModel deserialize(String topic, byte[] data) {
        try {
            return new ObjectMapper().readValue(data, KafkaMessageModel.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // TODO Auto-generated method stub
    }

    @Override
    public byte[] serialize(String topic, KafkaMessageModel data) {

        try {
            return new ObjectMapper().writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
    }

}
