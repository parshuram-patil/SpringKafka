/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.cs.kafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.cs.interactors.kafka.listerners.KafkaJsonMessageListenerInteractor;
import com.cs.interactors.kafka.listerners.KafkaMessageListenerInteractor;
import com.cs.model.kafka.KafkaMessageModel;

@EnableKafka
@Configuration
public class KafkaConfiguration {
  
  @Value("${kafka.bootstrap.servers}")
  private String  bootStrapServers;
  
  @Value("${kafka.acks}")
  private String  acks;
  
  @Value("${kafka.retries}")
  private Integer retries;
  
  @Value("${kafka.batch.size}")
  private Integer batchSize;
  
  @Value("${kafka.linger.ms}")
  private Long    lingerMs;
  
  @Value("${kafka.buffer.memory}")
  private Long    bufferMemory;
  
  @Value("${kafka.auto.commit.interval.ms}")
  private Integer autoCommitInterval;
  
  @Value("${kafka.session.timeout.ms}")
  private Integer sessionTimeoutMs;
  
  @Value("${kafka.max.poll.interval.ms}")
  private Integer maxPollIntervalMs;
  
  @Value("${kafka.max.poll.records}")
  private Integer maxPollRecords;
  
  @Value("${kafka.concurrency}")
  private Integer concurrency;
  
  @Value("${kafka.topic}")
  private String  topic;
  
  @Value("${kafka.group.id}")
  private String  groupID;
  
  @Value("${kafka.raw.topic}")
  private String  rawTopic;
  
  @Value("${kafka.raw.group.id}")
  private String  rawGroupID;
  
  @Value("${kafka.json.topic}")
  private String  jsonTopic;
  
  @Value("${kafka.json.group.id}")
  private String  jsonGroupID;
  
  @Bean
  public ProducerFactory<String, String> producerFactory()
  {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    props.put(ProducerConfig.RETRIES_CONFIG, retries);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
    props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    return new DefaultKafkaProducerFactory<>(props);
  }
  
  @Bean
  public ProducerFactory<String, KafkaMessageModel> jsonProducerFactory()
  {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    props.put(ProducerConfig.RETRIES_CONFIG, retries);
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
    props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(props);
  }
  
  @Bean
  public KafkaTemplate<String, String> kafkaTemplate()
  {
    
    return new KafkaTemplate<>(producerFactory());
  }
  
  @Bean
  public KafkaTemplate<String, KafkaMessageModel> jsonKafkaTemplate()
  {
    
    return new KafkaTemplate<>(jsonProducerFactory());
  }
  
  @Bean
  public ConsumerFactory<String, String> consumerFactory()
  {
    
    return new DefaultKafkaConsumerFactory<>(consumerProperties());
  }
  
  @Bean
  public ConsumerFactory<String, String> rawConsumerFactory()
  {
    
    return new DefaultKafkaConsumerFactory<>(rawConsumerProperties());
  }
  
  @Bean
  public ConsumerFactory<String, KafkaMessageModel> jsonConsumerFactory()
  {
    JsonDeserializer<KafkaMessageModel> jsonDeserializer = new JsonDeserializer<>(KafkaMessageModel.class);
    
    return new DefaultKafkaConsumerFactory<>(jsonConsumerProperties(), new StringDeserializer(), jsonDeserializer);
  }
  
  @Bean
  public Map<String, Object> consumerProperties()
  {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
    
    return props;
  }
  
  @Bean
  public Map<String, Object> rawConsumerProperties() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, rawGroupID);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
  
    return props;
  }
  
  @Bean
  public Map<String, Object> jsonConsumerProperties() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, jsonGroupID);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMs);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
  
    return props;
  }
  
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory()
  {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setConcurrency(concurrency);
    factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
    factory.getContainerProperties().setConsumerTaskExecutor(execC());
    factory.getContainerProperties().setListenerTaskExecutor(execL());
    
    return factory;
  }
  
  @Bean
  public AsyncListenableTaskExecutor execC()
  {
    ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
    tpte.setThreadGroupName("execC Group");
    tpte.setThreadNamePrefix("-execC_Thread-");
    tpte.setCorePoolSize(10);
    
    return tpte;
  }
  
  @Bean
  public AsyncListenableTaskExecutor execL()
  {
    ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();
    tpte.setCorePoolSize(10);
    tpte.setThreadGroupName("execL Group");
    tpte.setThreadNamePrefix("-execL_Thread-");
    
    return tpte;
  }
  
  /*@Bean
  public ConcurrentMessageListenerContainer<String, String> container(
      ConsumerFactory<String, String> rawConsumerFactory) {
      ContainerProperties containerProperties = new ContainerProperties(new String[] { rawTopic });
      containerProperties.setMessageListener(new KafkaMessageListenerInteractor());
      containerProperties.setAckMode(AckMode.MANUAL_IMMEDIATE);
      ConcurrentMessageListenerContainer<String, String> container = new ConcurrentMessageListenerContainer<>(rawConsumerFactory, containerProperties);
      container.setConcurrency(concurrency);
      //container.start();
      return container;
  }*/
  
  @Bean
  public KafkaMessageListenerContainer<String, String> container(
      ConsumerFactory<String, String> rawConsumerFactory) {
    ContainerProperties containerProperties = new ContainerProperties(new String[] { rawTopic });
    containerProperties.setMessageListener(new KafkaMessageListenerInteractor());
    containerProperties.setAckMode(AckMode.MANUAL_IMMEDIATE);
    
    return new KafkaMessageListenerContainer<>(rawConsumerFactory, containerProperties);
  }

  @Bean
  public ConcurrentMessageListenerContainer<String, KafkaMessageModel> jsonContainer(
      ConsumerFactory<String, KafkaMessageModel> jsonConsumerFactory) {
      ContainerProperties containerProperties = new ContainerProperties(new String[] { jsonTopic });
      containerProperties.setMessageListener(new KafkaJsonMessageListenerInteractor());
      containerProperties.setAckMode(AckMode.MANUAL_IMMEDIATE);
      ConcurrentMessageListenerContainer<String, KafkaMessageModel> container = new ConcurrentMessageListenerContainer<>(jsonConsumerFactory, containerProperties);
      container.setConcurrency(concurrency);
      
      return container;
  }
}