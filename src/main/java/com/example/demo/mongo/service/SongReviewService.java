package com.example.demo.mongo.service;

import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mongo.document.Review;
import com.example.demo.mongo.document.SongReview;
import com.example.demo.mongo.dto.SongReviewDtos.SongReviewRequest;
import com.example.demo.mongo.dto.SongReviewDtos.SongReviewResponse;
import com.example.demo.mongo.repository.SongReviewRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Business logic for song_reviews CRUD operations in MongoDB.
 */
@Service
public class SongReviewService {

    private final SongReviewRepository songReviewRepository;

    public SongReviewService(SongReviewRepository songReviewRepository) {
        this.songReviewRepository = songReviewRepository;
    }

    public List<SongReviewResponse> findAll() {
        return songReviewRepository.findAll().stream()
                .map(SongReviewResponse::from)
                .toList();
    }

    public SongReviewResponse findById(String id) {
        return SongReviewResponse.from(getDocumentOrThrow(id));
    }

    public SongReviewResponse findBySongId(Integer songId) {
        return songReviewRepository.findBySongId(songId)
                .map(SongReviewResponse::from)
                .orElseThrow(() -> new ResourceNotFoundException("Song review not found for song_id: " + songId));
    }

    public SongReviewResponse create(SongReviewRequest request) {
        validateRequest(request);

        if (songReviewRepository.existsBySongId(request.songId())) {
            throw new ConflictException("Song review already exists for song_id: " + request.songId());
        }

        SongReview document = mapToDocument(request);
        document.setCreatedAt(Instant.now());
        document.setUpdatedAt(Instant.now());
        applyComputedFields(document);

        return SongReviewResponse.from(songReviewRepository.save(document));
    }

    public SongReviewResponse update(String id, SongReviewRequest request) {
        validateRequest(request);

        SongReview document = getDocumentOrThrow(id);

        if (!document.getSongId().equals(request.songId())
                && songReviewRepository.existsBySongId(request.songId())) {
            throw new ConflictException("Song review already exists for song_id: " + request.songId());
        }

        applyRequest(document, request);
        document.setUpdatedAt(Instant.now());
        applyComputedFields(document);

        return SongReviewResponse.from(songReviewRepository.save(document));
    }

    public void delete(String id) {
        SongReview document = getDocumentOrThrow(id);
        songReviewRepository.delete(document);
    }

    private SongReview getDocumentOrThrow(String id) {
        return songReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song review not found: " + id));
    }

    private void validateRequest(SongReviewRequest request) {
        if (request.songId() == null) {
            throw new IllegalArgumentException("songId is required");
        }
        if (request.songTitle() == null || request.songTitle().isBlank()) {
            throw new IllegalArgumentException("songTitle is required");
        }
        if (request.artist() == null || request.artist().isBlank()) {
            throw new IllegalArgumentException("artist is required");
        }
    }

    private SongReview mapToDocument(SongReviewRequest request) {
        SongReview document = new SongReview();
        applyRequest(document, request);
        return document;
    }

    private void applyRequest(SongReview document, SongReviewRequest request) {
        document.setSongId(request.songId());
        document.setSongTitle(request.songTitle());
        document.setArtist(request.artist());
        document.setTags(request.tags() != null ? request.tags() : List.of());
        document.setReviews(request.reviews() != null ? request.reviews() : List.of());
        document.setPlayHistory(request.playHistory() != null ? request.playHistory() : List.of());
    }

    private void applyComputedFields(SongReview document) {
        document.setTotalPlays(document.getPlayHistory().size());
        document.setAverageRating(calculateAverageRating(document.getReviews()));
    }

    private Double calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        double sum = reviews.stream()
                .filter(review -> review.getRating() != null)
                .mapToInt(Review::getRating)
                .sum();
        long count = reviews.stream()
                .filter(review -> review.getRating() != null)
                .count();
        return count == 0 ? 0.0 : sum / count;
    }
}
