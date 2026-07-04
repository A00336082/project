package com.example.demo.mysql.dto;

import com.example.demo.mysql.entity.Playlist;
import com.example.demo.mysql.projection.TopPlaylistView;

import java.time.LocalDateTime;

public final class PlaylistDtos {

    private PlaylistDtos() {
    }

    public record PlaylistRequest(
            Integer userId,
            String name,
            String description
    ) {
    }

    public record PlaylistResponse(
            Integer playlistId,
            Integer userId,
            String username,
            String name,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static PlaylistResponse from(Playlist playlist) {
            return new PlaylistResponse(
                    playlist.getPlaylistId(),
                    playlist.getUser().getUserId(),
                    playlist.getUser().getUsername(),
                    playlist.getName(),
                    playlist.getDescription(),
                    playlist.getCreatedAt(),
                    playlist.getUpdatedAt()
            );
        }
    }

    public record TopPlaylistResponse(
            Integer playlistId,
            Integer userId,
            String name,
            String description,
            LocalDateTime createdAt,
            String username,
            Long songCount
    ) {
        public static TopPlaylistResponse from(TopPlaylistView view) {
            return new TopPlaylistResponse(
                    view.getPlaylistId(),
                    view.getUserId(),
                    view.getName(),
                    view.getDescription(),
                    view.getCreatedAt(),
                    view.getUsername(),
                    view.getSongCount()
            );
        }
    }
}
