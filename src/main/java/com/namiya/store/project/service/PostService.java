package com.namiya.store.project.service;

import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    Result<Post> publish(Post post);
    Result<List<Post>> getAll();
}
