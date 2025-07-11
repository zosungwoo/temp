package com.example.demo;

public record MemberEmbeddingStatusMessage(
        Long memberId,
        boolean isSuccess
) {
}