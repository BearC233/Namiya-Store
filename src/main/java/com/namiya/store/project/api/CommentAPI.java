package com.namiya.store.project.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.namiya.store.project.model.Comment;
import com.namiya.store.project.model.Paging;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.service.CommentService;
import com.namiya.store.project.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/comment/api")
@Controller
@Tag(name = "评论api")
public class CommentAPI {
    @Autowired
        CommentService commentService;
    @PostMapping("/publish")
    @ResponseBody
    @Operation(summary = "发布评论",description = "要求前端提供评论内容,评论标题,评论发布的用户,将评论储存到服务器中")
    Result<Comment> publish(@Parameter(description = "要求提供评论内容,标题,发布用户") Comment comment,
                            @Parameter(description = "可以选择是否添加图片",required = false) MultipartFile image){
        return image==null? commentService.publish(comment) : commentService.publish(comment,image);
    }
    @GetMapping("/getAll")
    @ResponseBody
    @Operation(summary = "分页查询所有评论",description = "提供所有评论,一页默认10个评论,返回结果两层result嵌套,注意层次")
    Result<Paging<Comment>> getAll(@Parameter(description = "页码", example = "0") @RequestParam(defaultValue = "0") int page,
                              @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int size
                              ){
        Result<Paging<Comment>> result = new Result();
        Page<Comment> page1 = PageHelper.startPage(page, size).doSelectPage(() -> commentService.getAll());

        result.setSuccess(true);
        result.setData(
                new Paging<>(page1.getPageNum(), page1.getPageSize(), page1.getPages(), page1.getTotal(), page1.getResult()));
        return result;

    }
    @PostMapping("/del")
    @ResponseBody
    @Operation(summary = "删除评论",description = "根据评论id删除数据库中的条目,返回值为0时代表删除失败")
    int del(@Parameter(description = "评论id")String commentId){
        return commentService.del(commentId);
    }
    @PostMapping("/update")
    @ResponseBody
    @Operation(summary = "更改评论",description = "根据评论id更改评论的内容,标题,热度,是否最佳")
    Result<Integer> update(@Parameter(description = "评论更改的model,必须含有id,可更改热度,最佳评论...")Comment comment,
                           @Parameter(description = "可以选择是否添加图片",required = false)MultipartFile image){
        return image==null? commentService.update(comment) : commentService.update(comment,image);
    }
    @GetMapping("/getComment")
    @ResponseBody
    @Operation(summary = "查询单个评论",description = "根据评论id查询该帖子")
    Result<Comment> query(@Parameter(description = "评论id")String commentId){
        return commentService.get(commentId);
    }
    @GetMapping("/getByUser")
    @ResponseBody
    @Operation(summary = "查询作者的所有评论",description = "根据作者名查询作者评论")
    Result<List<Comment>> queryByUser(@Parameter(description = "作者名(不支持模糊搜索)")String userName){
        return commentService.getByUser(userName);
    }
}
