package com.example.sandbox.kafka.democonsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@SpringBootApplication
public class KafkaConsumer {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumer.class, args);
	}

	@KafkaListener(topics = "first-sandbox-topic")
	public void listen(@Payload String message,
					   @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
					   @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){
		System.out.println("Message received: " +  key + " partId: " + partitionId + " message: " + message);
	}

}
