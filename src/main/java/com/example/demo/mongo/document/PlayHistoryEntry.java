package com.example.demo.mongo.document;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Embedded play event inside the play_history array of a SongReview document.
 */
public class PlayHistoryEntry {

    @Field("user_id")
    private Integer userId;

    private String username;

    @Field("played_at")
    private String playedAt;

    @Field("duration_listened_sec")
    private Integer durationListenedSec;

    private Boolean completed;

    public PlayHistoryEntry() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(String playedAt) {
        this.playedAt = playedAt;
    }

    public Integer getDurationListenedSec() {
        return durationListenedSec;
    }

    public void setDurationListenedSec(Integer durationListenedSec) {
        this.durationListenedSec = durationListenedSec;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
