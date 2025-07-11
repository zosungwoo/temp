package com.example.demo;

import java.util.List;

public record MemberInteractionMessage(
        InteractionType interactionType,
        Long memberId,
        List<Long> restaurantIdList
) {
    public static MemberInteractionMessage of(InteractionType interactionType, Long memberId, List<Long> restaurantIdList) {
        return new MemberInteractionMessage(interactionType, memberId, restaurantIdList);
    }
}
