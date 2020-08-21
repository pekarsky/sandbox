package com.example.sandbox.kafka.requestreplyprocessor;

import com.example.sandbox.kafka.model.Model;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
public class RequestReplyProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestReplyProcessorApplication.class, args);
	}

	@KafkaListener(topics = "request-reply-topic")
	@SendTo
	public Model listen(Model request){
		request.setAcknowledgement("received");
		return request;
	}
}
