package com.namiya.store.project.service.impl;

import com.namiya.store.project.dao.CommentDAO;
import com.namiya.store.project.dao.PostDAO;
import com.namiya.store.project.dataobject.CommentDO;
import com.namiya.store.project.dataobject.PostDO;
import com.namiya.store.project.model.Comment;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.CommentService;
import com.namiya.store.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDAO commentDAO;
    @Autowired
    UserService userService;
    @Override
    public Result<Comment> publish(Comment comment) {
        Result<Comment> result = new Result<>();
        if(comment.getCommentContent()==null){
            result.setSuccess(false);
            result.setCode("101");
            result.setCode("内容不可为空!");
            return result;
        }

        CommentDO commentDO=new CommentDO(comment);
        int insert = commentDAO.insert(commentDO);
        if(insert>0){
            result.setData(comment);
            result.setSuccess(true);
            result.setMessage("发布成功!");
            return result;
        }
        else{
            result.setSuccess(false);
            result.setCode("102");
            result.setCode("发布失败!请稍后再试");
            return result;
        }
    }

    @Override
    public Result<Comment> get(String commentId) {
        Result<Comment> result = new Result<>();
        if(commentId==null){
            result.setSuccess(false);
            result.setMessage("请输入帖子ID!");
            result.setCode("101");
            return result;
        }
        CommentDO byId = commentDAO.findById(commentId);
        if(byId!=null) {
            result.setSuccess(true);
            result.setData(byId.toModel());
            return result;
        }
        else{
            result.setSuccess(false);
            result.setMessage("无数据");
            result.setCode("102");
            return result;
        }
    }

    @Override
    public Result<List<Comment>> getByUser(String userName) {
        Result<List<Comment>> result = new Result<>();
        if(userName==null){
            result.setSuccess(false);
            result.setMessage("请输入用户名!");
            result.setCode("101");
            return result;
        }
        Result<User> byName = userService.findByName(userName);
        if(!byName.isSuccess()) {
            result.setSuccess(false);
            result.setCode("104");
            result.setMessage("无用户");
            return result;
        }
        List<CommentDO> byTitle = commentDAO.findByUser(byName.getData().getUserId());
        if(byTitle.isEmpty()){
            result.setSuccess(false);
            result.setMessage("无数据!");
            result.setCode("103");
            return result;
        }
        List<Comment> list = byTitle.stream().map(CommentDO::toModel).toList();
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result<List<Comment>> getAll() {
        Result<List<Comment>> result = new Result();
        List<CommentDO> all = commentDAO.findAll();
        if(all.isEmpty()){
            result.setSuccess(false);
            result.setCode("303");
            result.setMessage("没有数据");
            return result;
        }
        List<Comment> collect = all.stream().map(PostDO -> {
            return PostDO.toModel();
        }).collect(Collectors.toList());
        result.setSuccess(true);
        result.setData(collect);
        return result;
    }

    @Override
    public int del(String commentId) {
        return commentDAO.delete(commentId);
    }

    @Override
    public Result<Integer> update(Comment comment) {
        Result<Integer> result=new Result<>();
        if(comment.getCommentId()==null){
            result.setSuccess(false);
            result.setCode("101");
            result.setMessage("无帖子id!");
            return result;
        }
        int update = commentDAO.update(new CommentDO(comment));
        if(update==0) {

            result.setSuccess(false);
            result.setMessage("更新失败!");
            result.setCode("102");
            return result;
        }
        else{
            result.setData(update);
            result.setSuccess(true);
            result.setData(update);
            return result;
        }
    }
}
