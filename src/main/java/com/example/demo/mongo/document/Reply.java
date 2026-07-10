package com.example.demo.mongo.document;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Embedded reply nested inside a Review document.
 */
public class Reply {

    @Field("user_id")
    private Integer userId;

    private String username;

    private String comment;

    private String date;

    public Reply() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
