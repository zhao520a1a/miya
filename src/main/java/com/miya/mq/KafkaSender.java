package com.miya.mq;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author 赵金鑫
 */
@Log4j2

@EnableBinding(Source.class)
public class KafkaSender {

    @Autowired
    private Source source;

    public void sendMessage(String message) {
        try {
            source.output().send(MessageBuilder.withPayload(message).build());
            System.out.println("KafkaSender发送消息成功,msg: " + message);
        } catch (Exception e) {
            log.info("Kafka消息发送失败，原因："+e);
            e.printStackTrace();
        }
    }



}
