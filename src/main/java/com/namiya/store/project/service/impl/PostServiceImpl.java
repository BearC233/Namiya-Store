package com.namiya.store.project.service.impl;

import com.namiya.store.project.dao.PostDAO;
import com.namiya.store.project.dataobject.PostDO;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class PostServiceImpl implements PostService {
    @Autowired
    PostDAO postDAO;
    @Override
    public Result<Post> publish(Post post) {
        Result<Post> result = new Result<>();
        if(post.getPostContent()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setCode("内容不可为空!");
            return result;
        }
        if(post.getPostTitle()==null){
            result.setSuccess(false);
            result.setCode("301");
            result.setCode("标题不可为空!");
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
            result.setCode("发布失败!请稍后再试");
            return result;
        }

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
}
