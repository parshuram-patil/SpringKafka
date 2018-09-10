package com.cs.kafka.serialiser;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import com.cs.proto.compile.klassInstance.KlassInstanceProto.KlassInstance;
import com.google.protobuf.InvalidProtocolBufferException;

public class KlassInstanceSerielizer implements Serializer<KlassInstance>,Deserializer<KlassInstance>
{

 @Override
 public KlassInstance deserialize(String topic, byte[] data)
 {
   //return (KlassInstance)SerializationUtils.deserialize(data);
	 try {
		return KlassInstance.parseFrom(data);
	} catch (InvalidProtocolBufferException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
 }

 @Override
 public void configure(Map<String, ?> configs, boolean isKey)
 {
   // TODO Auto-generated method stub
   
 }

 @Override
 public byte[] serialize(String topic, KlassInstance data)
 {
   //return SerializationUtils.serialize(data);
	 byte[] byteArray = data.toByteArray();
	 System.out.println("\n ********************* Serialised Object Size ----> " + byteArray.length);
	 return byteArray;
 }

 @Override
 public void close()
 {
   
 }
  
}
