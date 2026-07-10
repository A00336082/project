package com.example.demo.mongo.repository;

import com.example.demo.mongo.document.SongReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the song_reviews collection.
 */
@Repository
public interface SongReviewRepository extends MongoRepository<SongReview, String>, SongReviewRepositoryCustom {

    Optional<SongReview> findBySongId(Integer songId);

    boolean existsBySongId(Integer songId);

    List<SongReview> findByArtist(String artist);

    List<SongReview> findByTagsContaining(String tag);
}
