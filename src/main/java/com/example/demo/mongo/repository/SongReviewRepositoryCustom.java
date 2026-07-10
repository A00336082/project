package com.example.demo.mongo.repository;

import com.example.demo.mongo.dto.TopTrackResponse;

import java.util.List;

/**
 * Custom repository contract for MongoDB aggregation pipelines
 */
public interface SongReviewRepositoryCustom {

    /**
     * Runs the top-tracks aggregation: unwind play_history, group by song_id,
     * count plays, sort descending, limit 10.
     */
    List<TopTrackResponse> getTopTracks();
}
