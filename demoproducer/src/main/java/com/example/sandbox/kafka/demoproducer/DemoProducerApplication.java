package com.example.sandbox.kafka.demoproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoProducerApplication {

	public static final String TOPIC = "first-sandbox-topic";

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;


	public static void main(String[] args) {
		SpringApplication.run(DemoProducerApplication.class, args);
	}

	@GetMapping("/send/{key}/{message}")
	public String sendToKafka(@PathVariable String key, @PathVariable String message){
		kafkaTemplate.send(TOPIC, key, message);
		return "OK";
	}

}
