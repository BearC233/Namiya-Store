package com.namiya.store.project.service.impl;

import com.namiya.store.project.dao.PostDAO;
import com.namiya.store.project.dataobject.PostDO;
import com.namiya.store.project.dataobject.UserDO;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.FileStorageService;
import com.namiya.store.project.service.PostService;
import com.namiya.store.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class PostServiceImpl implements PostService {
    @Autowired
    PostDAO postDAO;
    @Autowired
    UserService userService;
    @Autowired
    FileStorageService fileStorageService;
    @Override
    public Result<Post> publish(Post post) {
        Result<Post> result = new Result<>();
        if(post.getPostContent()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setMessage("内容不可为空!");
            return result;
        }
        if(post.getPostTitle()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setMessage("标题不可为空!");
            return result;
        }
        PostDO postDO=new PostDO(post);
        int insert = postDAO.insert(postDO);
        if(insert>0){
            result.setData(post);
            result.setSuccess(true);
            result.setMessage("发布成功!");
            return result;
        }
        else{
            result.setSuccess(false);
            result.setCode("302");
            result.setMessage("发布失败!请稍后再试");
            return result;
        }

    }

    @Override
    public Result<Post> publish(Post post, MultipartFile image) {
        Result<Post> result = new Result<>();
        if(!isImage(image)){
            result.setSuccess(false);
            result.setCode("305");
            result.setMessage("不支持非图片格式!");
            return result;
        }
        if(post.getPostContent()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setMessage("内容不可为空!");
            return result;
        }
        if(post.getPostTitle()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setMessage("标题不可为空!");
            return result;
        }
        try {
            post.setImg(fileStorageService.storeFile(image));
        } catch (IOException e) {
            result.setSuccess(false);
            result.setCode("306");
            result.setMessage("图片上传失败!");
            return result;
        }
        PostDO postDO=new PostDO(post);
        int insert = postDAO.insert(postDO);
        if(insert>0){
            result.setData(post);
            result.setSuccess(true);
            result.setMessage("发布成功!");
            return result;
        }
        else{
            result.setSuccess(false);
            result.setCode("302");
            result.setMessage("发布失败!请稍后再试");
            return result;
        }

    }

    @Override
    public Result<Post> get(String postId) {
        Result<Post> result = new Result<>();
        if(postId==null){
            result.setSuccess(false);
            result.setMessage("请输入帖子ID!");
            result.setCode("301");
            return result;
        }
        PostDO byId = postDAO.findById(postId);
        if(byId!=null) {
            result.setSuccess(true);
            result.setData(byId.toModel());
            return result;
        }
        else{
            result.setSuccess(false);
            result.setMessage("无数据");
            result.setCode("302");
            return result;
        }
    }

    @Override
    public Result<List<Post>> getByTitle(String title) {
        Result<List<Post>> result = new Result<>();
        if(title==null){
            result.setSuccess(false);
            result.setMessage("请输入帖子标题!");
            result.setCode("301");
            return result;
        }
        List<PostDO> byTitle = postDAO.findByTitle(title);
        if(byTitle.isEmpty()){
            result.setSuccess(false);
            result.setMessage("无数据!");
            result.setCode("303");
            return result;
        }
        List<Post> list = byTitle.stream().map(PostDO::toModel).toList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result<List<Post>> getByUser(String userName) {
        Result<List<Post>> result = new Result<>();
        if(userName==null){
            result.setSuccess(false);
            result.setMessage("请输入用户名!");
            result.setCode("301");
            return result;
        }
        Result<User> byName = userService.findByName(userName);
        if(!byName.isSuccess()) {
            result.setSuccess(false);
            result.setCode("304");
            result.setMessage("无用户");
            return result;
        }
        List<PostDO> byTitle = postDAO.findByUser(byName.getData().getUserId());
        if(byTitle.isEmpty()){
            result.setSuccess(false);
            result.setMessage("无数据!");
            result.setCode("303");
            return result;
        }
        List<Post> list = byTitle.stream().map(PostDO::toModel).toList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result<List<Post>> getAll() {
        Result<List<Post>> result = new Result();
        List<PostDO> all = postDAO.findAll();
        if(all.isEmpty()){
            result.setSuccess(false);
            result.setCode("303");
            result.setMessage("没有数据");
            return result;
        }
        List<Post> collect = all.stream().map(PostDO -> {
            return PostDO.toModel();
        }).collect(Collectors.toList());
        result.setSuccess(true);
        result.setData(collect);
        return result;
    }

    @Override
    public int del(String postId) {
        return postDAO.delete(postId);
    }

    @Override
    public Result<Integer> update(Post post) {
        Result<Integer> result=new Result<>();
        if(post.getPostId()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setMessage("无帖子id!");
            return result;
        }
        int update = postDAO.update(new PostDO(post));
        if(update==0) {

            result.setSuccess(false);
            result.setMessage("更新失败!");
            result.setCode("302");
            return result;
        }
        else{
            result.setData(update);
            result.setSuccess(true);
            result.setData(update);
            return result;
        }
    }

    @Override
    public Result<Integer> update(Post post, MultipartFile image) {
        Result<Integer> result=new Result<>();
        if(!isImage(image)){
            result.setSuccess(false);
            result.setCode("305");
            result.setMessage("不支持非图片格式!");
            return result;
        }
        if(post.getPostId()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setMessage("无帖子id!");
            return result;
        }
        try {
            post.setImg(fileStorageService.storeFile(image));
        } catch (IOException e) {
            result.setSuccess(false);
            result.setCode("306");
            result.setMessage("图片上传失败!");
            return result;
        }
        int update = postDAO.update(new PostDO(post));
        if(update==0) {

            result.setSuccess(false);
            result.setMessage("更新失败!");
            result.setCode("302");
            return result;
        }
        else{
            result.setData(update);
            result.setSuccess(true);
            result.setData(update);
            return result;
        }
    }
    private boolean isImage(MultipartFile file){
        String contentType=file.getContentType();
        return contentType!=null&&contentType.startsWith("image/");
    }
}
