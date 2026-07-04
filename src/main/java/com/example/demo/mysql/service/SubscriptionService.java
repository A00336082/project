package com.example.demo.mysql.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mysql.dto.SubscriptionDtos.SubscriptionRequest;
import com.example.demo.mysql.dto.SubscriptionDtos.SubscriptionResponse;
import com.example.demo.mysql.entity.Subscription;
import com.example.demo.mysql.entity.User;
import com.example.demo.mysql.enums.SubscriptionStatus;
import com.example.demo.mysql.repository.SubscriptionRepository;
import com.example.demo.mysql.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for subscription plan management.
 */
@Service
@Transactional
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<SubscriptionResponse> findAll() {
        return subscriptionRepository.findAll().stream()
                .map(SubscriptionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubscriptionResponse findById(Integer id) {
        return SubscriptionResponse.from(getSubscriptionOrThrow(id));
    }

    public SubscriptionResponse create(SubscriptionRequest request) {
        validateRequest(request);

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId()));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlanType(request.planType());
        subscription.setStartDate(request.startDate());
        subscription.setEndDate(request.endDate());
        subscription.setStatus(request.status() != null ? request.status() : SubscriptionStatus.ACTIVE);

        return SubscriptionResponse.from(subscriptionRepository.save(subscription));
    }

    public SubscriptionResponse update(Integer id, SubscriptionRequest request) {
        validateRequest(request);

        Subscription subscription = getSubscriptionOrThrow(id);
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId()));

        subscription.setUser(user);
        subscription.setPlanType(request.planType());
        subscription.setStartDate(request.startDate());
        subscription.setEndDate(request.endDate());
        subscription.setStatus(request.status() != null ? request.status() : subscription.getStatus());

        return SubscriptionResponse.from(subscriptionRepository.save(subscription));
    }

    public void delete(Integer id) {
        Subscription subscription = getSubscriptionOrThrow(id);
        subscriptionRepository.delete(subscription);
    }

    private Subscription getSubscriptionOrThrow(Integer id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found: " + id));
    }

    private void validateRequest(SubscriptionRequest request) {
        if (request.userId() == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (request.planType() == null) {
            throw new IllegalArgumentException("planType is required");
        }
        if (request.startDate() == null) {
            throw new IllegalArgumentException("startDate is required");
        }
        if (request.endDate() == null) {
            throw new IllegalArgumentException("endDate is required");
        }
    }
}
