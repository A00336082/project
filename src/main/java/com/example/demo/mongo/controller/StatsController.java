package com.example.demo.mongo.controller;

import com.example.demo.mongo.dto.TopTrackResponse;
import com.example.demo.mongo.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for MongoDB analytics endpoints.
 */
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * Runs the top-tracks aggregation pipeline on play_history.
     */
    @GetMapping("/top-tracks")
    public List<TopTrackResponse> getTopTracks() {
        return statsService.getTopTracks();
    }
}
