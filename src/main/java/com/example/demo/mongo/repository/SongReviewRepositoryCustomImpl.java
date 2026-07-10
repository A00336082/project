package com.example.demo.mongo.repository;

import com.example.demo.mongo.dto.TopTrackResponse;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implements the mandatory top-tracks aggregation pipeline using MongoTemplate.
 * Pipeline stages: $unwind -> $group -> $sort -> $limit
 */
@Repository
public class SongReviewRepositoryCustomImpl implements SongReviewRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public SongReviewRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<TopTrackResponse> getTopTracks() {
        UnwindOperation unwind = Aggregation.unwind("play_history");

        GroupOperation group = Aggregation.group("song_id")
                .first("song_title").as("songTitle")
                .first("artist").as("artist")
                .count().as("totalPlays");

        Aggregation aggregation = Aggregation.newAggregation(
                unwind,
                group,
                Aggregation.sort(Sort.Direction.DESC, "totalPlays"),
                Aggregation.limit(10)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                "song_reviews",
                Document.class
        );

        return results.getMappedResults().stream()
                .map(this::mapToTopTrack)
                .toList();
    }

    private TopTrackResponse mapToTopTrack(Document doc) {
        Object id = doc.get("_id");
        Integer songId = id instanceof Number number ? number.intValue() : null;
        return new TopTrackResponse(
                songId,
                doc.getString("songTitle"),
                doc.getString("artist"),
                doc.get("totalPlays", Number.class).longValue()
        );
    }
}
