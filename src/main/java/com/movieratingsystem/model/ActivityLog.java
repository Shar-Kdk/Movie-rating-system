package com.movieratingsystem.model;

import java.sql.Timestamp;

public class ActivityLog {
    private int logId;
    private Integer userId;  // Using Integer to allow null for anonymous users
    private String activityType;
    private String description;
    private Timestamp timestamp;

    public ActivityLog(int logId, Integer userId, String activityType, String description, Timestamp timestamp) {
        this.logId = logId;
        this.userId = userId;
        this.activityType = activityType;
        this.description = description;
        this.timestamp = timestamp;
    }

    // Constructor without logId for new logs
    public ActivityLog(Integer userId, String activityType, String description, Timestamp timestamp) {
        this.userId = userId;
        this.activityType = activityType;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ActivityLog{" +
                "logId=" + logId +
                ", userId=" + userId +
                ", activityType='" + activityType + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
} 