package com.example.demo.mongo.dto;

import com.example.demo.mongo.document.PlayHistoryEntry;
import com.example.demo.mongo.document.Review;
import com.example.demo.mongo.document.SongReview;

import java.time.Instant;
import java.util.List;

public final class SongReviewDtos {

    private SongReviewDtos() {
    }

    public record SongReviewRequest(
            Integer songId,
            String songTitle,
            String artist,
            List<String> tags,
            List<Review> reviews,
            List<PlayHistoryEntry> playHistory
    ) {
    }

    public record SongReviewResponse(
            String id,
            Integer songId,
            String songTitle,
            String artist,
            List<String> tags,
            List<Review> reviews,
            List<PlayHistoryEntry> playHistory,
            Integer totalPlays,
            Double averageRating,
            Instant createdAt,
            Instant updatedAt
    ) {
        public static SongReviewResponse from(SongReview document) {
            return new SongReviewResponse(
                    document.getId(),
                    document.getSongId(),
                    document.getSongTitle(),
                    document.getArtist(),
                    document.getTags(),
                    document.getReviews(),
                    document.getPlayHistory(),
                    document.getTotalPlays(),
                    document.getAverageRating(),
                    document.getCreatedAt(),
                    document.getUpdatedAt()
            );
        }
    }
}
