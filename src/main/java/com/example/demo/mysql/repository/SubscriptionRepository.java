package com.example.demo.mysql.repository;

import com.example.demo.mysql.entity.Subscription;
import com.example.demo.mysql.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Subscriptions table.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findByUserUserId(Integer userId);

    List<Subscription> findByStatus(SubscriptionStatus status);
}
