package com.example.demo.mysql.dto;

import com.example.demo.mysql.entity.Subscription;
import com.example.demo.mysql.enums.PlanType;
import com.example.demo.mysql.enums.SubscriptionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class SubscriptionDtos {

    private SubscriptionDtos() {
    }

    public record SubscriptionRequest(
            Integer userId,
            PlanType planType,
            LocalDate startDate,
            LocalDate endDate,
            SubscriptionStatus status
    ) {
    }

    public record SubscriptionResponse(
            Integer subscriptionId,
            Integer userId,
            PlanType planType,
            LocalDate startDate,
            LocalDate endDate,
            SubscriptionStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static SubscriptionResponse from(Subscription subscription) {
            return new SubscriptionResponse(
                    subscription.getSubscriptionId(),
                    subscription.getUser().getUserId(),
                    subscription.getPlanType(),
                    subscription.getStartDate(),
                    subscription.getEndDate(),
                    subscription.getStatus(),
                    subscription.getCreatedAt(),
                    subscription.getUpdatedAt()
            );
        }
    }
}
