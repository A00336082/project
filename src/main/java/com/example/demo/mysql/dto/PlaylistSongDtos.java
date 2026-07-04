package com.example.demo.mysql.dto;

import com.example.demo.mysql.entity.PlaylistSong;

import java.time.LocalDateTime;

public final class PlaylistSongDtos {

    private PlaylistSongDtos() {
    }

    public record PlaylistSongRequest(
            Integer playlistId,
            Integer songId,
            Integer position
    ) {
    }

    public record PlaylistSongResponse(
            Integer playlistSongId,
            Integer playlistId,
            Integer songId,
            String songTitle,
            Integer position,
            LocalDateTime addedAt
    ) {
        public static PlaylistSongResponse from(PlaylistSong playlistSong) {
            return new PlaylistSongResponse(
                    playlistSong.getPlaylistSongId(),
                    playlistSong.getPlaylist().getPlaylistId(),
                    playlistSong.getSong().getSongId(),
                    playlistSong.getSong().getTitle(),
                    playlistSong.getPosition(),
                    playlistSong.getAddedAt()
            );
        }
    }
}
