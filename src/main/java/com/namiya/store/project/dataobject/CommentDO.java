package com.namiya.store.project.dataobject;

import com.namiya.store.project.model.Comment;
import com.namiya.store.project.model.Comment;

import java.time.LocalDateTime;

public class CommentDO {
    private String commentId;
    private int commentHeat;
    private String commentUser;
    private String commentContent;
    private String img;
    private int best;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;

    public CommentDO(){}
    public CommentDO(Comment comment){
        this.commentId=comment.getCommentId();
        this.commentUser=comment.getCommentUser();
        this.commentContent=comment.getCommentContent();
        this.best=comment.getBest();
        this.img=comment.getImg();
        this.commentHeat=comment.getCommentHeat();
        this.gmtCreated=comment.getGmtCreated();
        this.gmtModified=comment.getGmtModified();
    }
    public Comment toModel(){
        Comment Comment = new Comment();
        Comment.setCommentUser(this.commentUser);
        Comment.setCommentContent(this.commentContent);
        Comment.setCommentHeat(this.commentHeat);
        Comment.setBest(this.best);
        Comment.setImg(this.img);
        Comment.setCommentId(this.commentId);
        Comment.setGmtCreated(this.gmtCreated);
        Comment.setGmtModified(this.gmtModified);
        return Comment;
    }
}
