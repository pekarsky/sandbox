package com.example.sandbox.kafka.requestreplyweb;

import com.example.sandbox.kafka.model.Model;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import static com.example.sandbox.kafka.requestreplyweb.DemoController.REQUEST_REPLY_TOPIC;

@SpringBootApplication
//@RequiredArgsConstructor
//@RestController
public class RequestReplyWebApplication {



	public static void main(String[] args) {
		SpringApplication.run(RequestReplyWebApplication.class, args);
	}



	@Bean
	public ReplyingKafkaTemplate<String, Model, Model>
	replyKafkaTemplate(ProducerFactory<String, Model> pf,
					   KafkaMessageListenerContainer<String, Model> container) {
		return new ReplyingKafkaTemplate<>(pf, container);
	}
	@Bean
	public KafkaMessageListenerContainer<String, Model> replyContainer(ConsumerFactory<String, Model> cf) {
		ContainerProperties containerProperties = new ContainerProperties(REQUEST_REPLY_TOPIC);
		return new KafkaMessageListenerContainer<>(cf, containerProperties);
	}
//	@Bean
//	public ReplyingKafkaTemplate<String, DemoController.Model, DemoController.Model> replyingTemplate(
//			ProducerFactory<String, DemoController.Model> pf,
//			ConcurrentMessageListenerContainer<String, DemoController.Model> repliesContainer) {
//
//		return new ReplyingKafkaTemplate<>(pf, repliesContainer);
//	}

//	@Bean
//	public ConcurrentMessageListenerContainer<String, String> repliesContainer(
//			ConcurrentKafkaListenerContainerFactory<String, String> containerFactory) {
//
//		ConcurrentMessageListenerContainer<String, String> repliesContainer =
//				containerFactory.createContainer("replies");
//		repliesContainer.getContainerProperties().setGroupId("repliesGroup");
//		repliesContainer.setAutoStartup(false);
//		return repliesContainer;
//	}


}
