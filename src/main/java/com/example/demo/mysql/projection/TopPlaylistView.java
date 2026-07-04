package com.example.demo.mysql.projection;

import java.time.LocalDateTime;

/**
 * Read-only projection returned by the Get_Top_Playlists stored procedure.
 */
public class TopPlaylistView {

    private final Integer playlistId;
    private final Integer userId;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final String username;
    private final Long songCount;

    public TopPlaylistView(
            Integer playlistId,
            Integer userId,
            String name,
            String description,
            LocalDateTime createdAt,
            String username,
            Long songCount) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.username = username;
        this.songCount = songCount;
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    public Long getSongCount() {
        return songCount;
    }
}
