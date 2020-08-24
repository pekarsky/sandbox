package com.example.sandbox.kafka.kafkastreamsdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.math.BigDecimal;

@Slf4j
@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaStreamsDemoConfig {

    @Bean
    public Serde<User> userSerde() {
        return Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(User.class));
    }

    @Bean
    public KStream<String, User> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, String> stream = kStreamBuilder
                .stream("usertopic-src", Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, User> userStream = stream
                .mapValues(this::getUserFromString)
                .filter((key, value) -> BigDecimal.ZERO.compareTo(value.getBalance()) > 0);
        userStream.to("usertopic-out", Produced.with(Serdes.String(), userSerde()));
        return userStream;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    User getUserFromString(String userString) {
        User user = null;
        try {
            user = objectMapper().readValue(userString, User.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return user;
    }
}
