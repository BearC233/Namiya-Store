package com.namiya.store.project.service;

import com.namiya.store.project.model.Comment;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface CommentService {
    Result<Comment> publish(Comment comment);
    Result<Comment> publish(Comment comment, MultipartFile image);
    Result<Comment> get(String commentId);
    Result<List<Comment>> getByUser(String userName);
    Result<List<Comment>> getAll();
    int del(String commentId);
    Result<Integer> update(Comment comment);
    Result<Integer> update(Comment comment,MultipartFile image);
}
