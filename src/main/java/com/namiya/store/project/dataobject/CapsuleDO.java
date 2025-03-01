package com.namiya.store.project.dataobject;

import com.namiya.store.project.model.Capsule;
import com.namiya.store.project.model.Status;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CapsuleDO implements Serializable {
    private String capsuleContent;
    private String capsuleId;
    private String capsuleUser;
    private LocalDateTime capsuleEnd;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    private Status Status;
    public CapsuleDO(){}
    public CapsuleDO(Capsule capsule){
        this.capsuleId=capsule.getCapsuleId();
        this.capsuleContent=capsule.getCapsuleContent();
        this.capsuleUser=capsule.getCapsuleUser();
        this.capsuleEnd=capsule.getCapsuleEnd();
        this.gmtCreated=capsule.getGmtCreated();
        this.gmtModified=capsule.getGmtModified();
        this.Status=capsule.getStatus();
    }

    public com.namiya.store.project.model.Status getStatus() {
        return Status;
    }

    public void setStatus(com.namiya.store.project.model.Status status) {
        Status = status;
    }

    public Capsule toModel(){
        Capsule capsule = new Capsule();
        capsule.setCapsuleId(this.capsuleId);
        capsule.setCapsuleUser(this.capsuleUser);
        capsule.setCapsuleContent(this.capsuleContent);
        capsule.setCapsuleEnd(this.capsuleEnd);
        capsule.setGmtCreated(this.gmtCreated);
        capsule.setGmtModified(this.gmtModified);
        capsule.setStatus(this.Status);
        return capsule;
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
