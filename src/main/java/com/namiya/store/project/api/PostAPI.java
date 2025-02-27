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
    Result<Post> publish(@Parameter(description = "要求提供帖子内容,标题,发布用户,标签")Post post){
        return postService.publish(post);
    }
    @GetMapping("/getPost")
    @ResponseBody
    @Operation(summary = "分页查询所有帖子",description = "提供所有帖子,一页默认10个帖子,返回结果两层result嵌套,注意层次")
    Result<Paging<Post>> getAll(@Parameter(description = "页码", example = "0") @RequestParam(defaultValue = "0") int page,
                              @Parameter(description = "每页数量", example = "10") @RequestParam(defaultValue = "10") int size,
                              @Parameter(description = "用户名过滤") @RequestParam(required = false) String username){
        Result<Paging<Post>> result = new Result();
        Page<Post> page1 = PageHelper.startPage(page, size).doSelectPage(() -> postService.getAll());

        result.setSuccess(true);
        result.setData(
                new Paging<>(page1.getPageNum(), page1.getPageSize(), page1.getPages(), page1.getTotal(), page1.getResult()));
        return result;

    }
}
