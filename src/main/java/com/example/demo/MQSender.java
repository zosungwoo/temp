package com.example.demo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MQSender {

    @Value("${rabbitmq.exchanges.embeddingStatus}")
    private String embeddingExchange;

    @Value("${rabbitmq.routing-keys.embeddingStatus}")
    private String embeddingRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    /**
     * 1. Queue 로 메세지를 발행
     * 2. Producer 역할 -> Direct Exchange 전략
     **/
    public void sendEmbeddingStatus(MemberEmbeddingStatusMessage message) {
        rabbitTemplate.convertAndSend(embeddingExchange, embeddingRoutingKey, message);
        log.info("RabbitMQ 유저 인터렉션 메시지 발행 성공 - isSuccess: {}, memberId: {}",
                message.isSuccess(), message.memberId());
    }

//    public void sendBatchJobRequest(String jobName, Map<String, Object> params) {
//        BatchJobRequest message = new BatchJobRequest(jobName, UUID.randomUUID().toString(), params);
//        rabbitTemplate.convertAndSend(batchExchange, batchRoutingKey, message);
//        log.info("배치 Job 요청 메시지 발행 성공 - jobName: {}, jobId: {}, params: {}",
//                message.jobName(), message.jobId(), message.params());
//    }
}
