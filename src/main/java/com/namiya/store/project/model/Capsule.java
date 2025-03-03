package com.namiya.store.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Capsule implements Serializable {
    private String capsuleContent;
    private String capsuleId;
    private String capsuleUser;
    private LocalDateTime capsuleEnd;
    private Status status;
    private String img;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCapsuleContent() {
        return capsuleContent;
    }

    public void setCapsuleContent(String capsuleContent) {
        this.capsuleContent = capsuleContent;
    }

    public String getCapsuleId() {
        return capsuleId;
    }

    public void setCapsuleId(String capsuleId) {
        this.capsuleId = capsuleId;
    }

    public String getCapsuleUser() {
        return capsuleUser;
    }

    public void setCapsuleUser(String capsuleUser) {
        this.capsuleUser = capsuleUser;
    }

    public LocalDateTime getCapsuleEnd() {
        return capsuleEnd;
    }

    public void setCapsuleEnd(LocalDateTime capsuleEnd) {
        this.capsuleEnd = capsuleEnd;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
