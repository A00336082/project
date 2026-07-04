package com.example.demo.mysql.dto;

import com.example.demo.mysql.entity.Song;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class SongDtos {

    private SongDtos() {
    }

    public record SongRequest(
            Integer artistId,
            String title,
            Integer durationSeconds,
            LocalDate releasedAt
    ) {
    }

    public record SongResponse(
            Integer songId,
            Integer artistId,
            String artistName,
            String title,
            Integer durationSeconds,
            LocalDate releasedAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static SongResponse from(Song song) {
            return new SongResponse(
                    song.getSongId(),
                    song.getArtist().getArtistId(),
                    song.getArtist().getName(),
                    song.getTitle(),
                    song.getDurationSeconds(),
                    song.getReleasedAt(),
                    song.getCreatedAt(),
                    song.getUpdatedAt()
            );
        }
    }
}
