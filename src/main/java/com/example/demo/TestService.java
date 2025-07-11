package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestService {

    private final EmbeddingRepository embeddingRepository;
//    private final MQSender mqSender;

    public void sendTestMessage(MemberInteractionMessage memberInteractionMessage) {
        try {
            float[] embed = embed();
            embeddingRepository.save(Embedding.builder().embedding(embed.toString()).build());
            // 테스트이므로 약 1초정도 걸리는 연산 수행할 것임
            int a = 1;
            int b = 1;
            long startTime = System.currentTimeMillis();
            long delay = 10; // 1초 (1000 밀리초)

//            Thread.sleep(delay); // 1초 대기 (테스트용)
            while (System.currentTimeMillis() - startTime < delay) {
                int c = a * (b++);
            }
        } catch (Exception e) {
            log.error("배치 작업 실패: {}", e.getMessage());  // 로깅만 수행 (예외 발생 시 ACK가 전송되지 않아 무한루프 발생)
            throw new RuntimeException("배치 작업 실패", e);
//            mqSender.sendEmbeddingStatus(new MemberEmbeddingStatusMessage(message.memberId(), false));
        }
        log.info(String.valueOf(System.currentTimeMillis()));
        log.info("성공");
    }

    public float[] embed() {
        // 신규 유저 임베딩 크기
        int embeddingSize = 64;
        // 신규 유저 임베딩 초기화
        float[] newUserEmbedding = new float[embeddingSize];
        Random rand = new Random();

        // 임베딩 초기화 (정규분포)
        for (int i = 0; i < embeddingSize; i++) {
            newUserEmbedding[i] = (float) rand.nextGaussian() * 0.1f;
        }

        // 5개의 식당을 선택했다고 가정
        List<float[]> restaurantEmbeddings = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            float[] restaurantEmbedding = new float[embeddingSize];
            for (int j = 0; j < embeddingSize; j++) {
                restaurantEmbedding[j] = (float) rand.nextGaussian() * 0.1f;
            }
            restaurantEmbeddings.add(restaurantEmbedding);
        }

        // 식당 임베딩을 더함
        for (float[] restaurantEmbedding : restaurantEmbeddings) {
            for (int i = 0; i < embeddingSize; i++) {
                newUserEmbedding[i] += restaurantEmbedding[i];
            }
        }

        // 정규화 (5로 나누기)
        for (int i = 0; i < embeddingSize; i++) {
            newUserEmbedding[i] /= 5;
        }

        // 새로운 유저 임베딩 출력 (예시)
//        for (float value : newUserEmbedding) {
//            System.out.print(value + " ");
//        }

        return newUserEmbedding;
    }
}
