package com.example.demo.mongo.service;

import com.example.demo.mongo.dto.TopTrackResponse;
import com.example.demo.mongo.repository.SongReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for MongoDB analytics endpoints.
 */
@Service
public class StatsService {

    private final SongReviewRepository songReviewRepository;

    public StatsService(SongReviewRepository songReviewRepository) {
        this.songReviewRepository = songReviewRepository;
    }

    /**
     * Returns the top 10 most-played songs based on play_history entries.
     */
    public List<TopTrackResponse> getTopTracks() {
        return songReviewRepository.getTopTracks();
    }
}
