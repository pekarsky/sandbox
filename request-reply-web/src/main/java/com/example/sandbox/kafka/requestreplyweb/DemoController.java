package com.example.sandbox.kafka.requestreplyweb;

import com.example.sandbox.kafka.model.Model;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DemoController {

    public static final String REQUEST_REPLY_TOPIC = "request-reply-topic";

    @Autowired
    private ReplyingKafkaTemplate<String, Model, Model> replyingKafkaTemplate;

    @GetMapping("/send-and-receive/{key}/{first}/{second}")
    public Model sendAndReturn(@PathVariable String first, @PathVariable String second) throws Exception {
        // create producer record
        ProducerRecord<String, Model> record = new ProducerRecord<>(REQUEST_REPLY_TOPIC, new Model(first, second, null));
        // set reply topic in header
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, REQUEST_REPLY_TOPIC.getBytes()));
        // post in kafka topic
        RequestReplyFuture<String, Model, Model> sendAndReceive = replyingKafkaTemplate.sendAndReceive(record);
        // confirm if producer produced successfully
//        SendResult<String, Model> sendResult = sendAndReceive.getSendFuture().get();
        // get consumer record
        ConsumerRecord<String, Model> consumerRecord = sendAndReceive.get();
        // return consumer value
        return consumerRecord.value();
    }



}


