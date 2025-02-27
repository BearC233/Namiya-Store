package com.namiya.store.project.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.namiya.store.project.model.Post;

import java.time.LocalDateTime;

public class PostDO {
    private String postTitle;
    private String postId;
    private int postHeat;
    private String postPublisher;
    private String postContent;
    private String tags;
    private int solved;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    public PostDO(){}
    public PostDO(Post Post){
        this.postId=Post.getPostId();
        this.postTitle=Post.getPostTitle();
        this.postPublisher=Post.getPostPublisher();
        this.postContent=Post.getPostContent();
        this.tags=Post.getTags();
        this.solved=Post.getSolved();
        this.postHeat=Post.getPostHeat();
        this.gmtCreated=Post.getGmtCreated();
        this.gmtModified=Post.getGmtModified();
    }
    public Post toModel(){
        Post Post = new Post();
        Post.setPostTitle(this.postTitle);
        Post.setPostPublisher(this.postPublisher);
        Post.setPostContent(this.postContent);
        Post.setPostHeat(this.postHeat);
        Post.setSolved(this.solved);
        Post.setTags(this.tags);
        Post.setPostId(this.postId);
        Post.setGmtCreated(this.gmtCreated);
        Post.setGmtModified(this.gmtModified);
        return Post;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getPostHeat() {
        return postHeat;
    }

    public void setPostHeat(int postHeat) {
        this.postHeat = postHeat;
    }

    public String getPostPublisher() {
        return postPublisher;
    }

    public void setPostPublisher(String postPublisher) {
        this.postPublisher = postPublisher;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
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
