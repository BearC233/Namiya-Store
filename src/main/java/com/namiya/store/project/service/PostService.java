package com.namiya.store.project.service;

import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface PostService {
    Result<Post> publish(Post post);
    Result<Post> publish(Post post, MultipartFile image);
    Result<Post> get(String postId);
    Result<List<Post>> getByTitle(String title);
    Result<List<Post>> getByUser(String userName);
    Result<List<Post>> getAll();
    int del(String postId);
    Result<Integer> update(Post post);
    Result<Integer> update(Post post,MultipartFile image);
}
