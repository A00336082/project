package com.example.demo.mongo.dto;

/**
 * Result of the top-tracks aggregation pipeline.
 */
public record TopTrackResponse(
        Integer songId,
        String songTitle,
        String artist,
        Long totalPlays
) {
}
