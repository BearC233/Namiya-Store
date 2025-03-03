package com.namiya.store.project.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.namiya.store.project.model.Paging;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/post/api")
@Controller
@Tag(name = "帖子api")
public class PostAPI {
    @Autowired
        PostService postService;
    @PostMapping("/publish")
    @ResponseBody
    @Operation(summary = "发布帖子",description = "要求前端提供帖子内容,帖子标题,帖子标签和帖子发布的用户,将帖子储存到服务器中")
    Result<Post> publish(@Parameter(description = "要求提供帖子内容,标题,发布用户,标签")Post post,
                         @Parameter(description = "可以选择是否添加图片",required = false) MultipartFile image){
        return image==null?postService.publish(post):postService.publish(post,image);
    }
    @GetMapping("/getAll")
    @ResponseBody
    @Operation(summary = "分页查询所有帖子",description = "提供所有帖子,一页默认10个帖子,返回结果两层result嵌套,注意层次")
    Result<Paging<Post>> getAll(@Parameter(description = "页码", example = "0") @RequestParam(defaultValue = "0") int page,
                              @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int size
                              ){
        Result<Paging<Post>> result = new Result();
        Page<Post> page1 = PageHelper.startPage(page, size).doSelectPage(() -> postService.getAll());

        result.setSuccess(true);
        result.setData(
                new Paging<>(page1.getPageNum(), page1.getPageSize(), page1.getPages(), page1.getTotal(), page1.getResult()));
        return result;

    }
    @PostMapping("/del")
    @ResponseBody
    @Operation(summary = "删除帖子",description = "根据帖子id删除数据库中的条目,返回值为0时代表删除失败")
    int del(@Parameter(description = "帖子id")String postId){
        return postService.del(postId);
    }
    @PostMapping("/update")
    @ResponseBody
    @Operation(summary = "更改帖子",description = "根据帖子id更改帖子的内容,标题,热度标签,是否解决")
    Result<Integer> update(@Parameter(description = "帖子更改的model,必须含有id,可更改热度,标签...")Post post,
                           @Parameter(description = "可以选择是否添加图片",required = false)MultipartFile image){
        return image==null?postService.update(post):postService.update(post,image);
    }
    @GetMapping("/getPost")
    @ResponseBody
    @Operation(summary = "查询单个帖子",description = "根据帖子id查询该帖子")
    Result<Post> query(@Parameter(description = "帖子id")String postId){
        return postService.get(postId);
    }
    @GetMapping("/getByTitle")
    @ResponseBody
    @Operation(summary = "查询标题类似的帖子",description = "根据帖子标题查询")
    Result<List<Post>> queryByTitle(@Parameter(description = "帖子标题(支持模糊搜索)")String postTitle){
        return postService.getByTitle(postTitle);
    }
    @GetMapping("/getByUser")
    @ResponseBody
    @Operation(summary = "查询作者的所有帖子",description = "根据作者名查询作者帖子")
    Result<List<Post>> queryByUser(@Parameter(description = "作者名(不支持模糊搜索)")String userName){
        return postService.getByUser(userName);
    }
}
