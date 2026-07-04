package com.example.demo.mysql.dto;

import com.example.demo.mysql.entity.Artist;

import java.time.LocalDateTime;

public final class ArtistDtos {

    private ArtistDtos() {
    }

    public record ArtistRequest(
            String name,
            String genre,
            String bio
    ) {
    }

    public record ArtistResponse(
            Integer artistId,
            String name,
            String genre,
            String bio,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static ArtistResponse from(Artist artist) {
            return new ArtistResponse(
                    artist.getArtistId(),
                    artist.getName(),
                    artist.getGenre(),
                    artist.getBio(),
                    artist.getCreatedAt(),
                    artist.getUpdatedAt()
            );
        }
    }
}
