package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MQListener {

    private final MQSender mqSender;

    @RabbitListener(queues = "${rabbitmq.queues.interaction}", concurrency = "4")
    public void receiveBatchJobRequest(MemberInteractionMessage message) {
        System.out.println("Received message: " + message);
        log.info("RabbitMQ 배치 작업 요청 메시지 수신 - interactionType: {}, memberId: {}, rstList: {}",
                message.interactionType(), message.memberId(), message.restaurantIdList());
        try {
            // 테스트이므로 약 1초정도 걸리는 연산 수행할 것임
            int a = 1;
            int b = 1;
            long startTime = System.currentTimeMillis();
            long delay = 1000; // 1초 (1000 밀리초)

            Thread.sleep(delay); // 1초 대기 (테스트용)
//            while (System.currentTimeMillis() - startTime < delay) {
//                int c = a * (b++);
//            }
        } catch (Exception e) {
            mqSender.sendEmbeddingStatus(new MemberEmbeddingStatusMessage(message.memberId(), false));
            log.error("배치 작업 실패: {}", e.getMessage());  // 로깅만 수행 (예외 발생 시 ACK가 전송되지 않아 무한루프 발생)
        }
        mqSender.sendEmbeddingStatus(new MemberEmbeddingStatusMessage(message.memberId(), true));
        log.info("RabbitMQ 배치 작업 성공");

    }
}
