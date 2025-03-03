package com.namiya.store.project.api;

import com.namiya.store.project.model.Capsule;
import com.namiya.store.project.model.Post;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.service.CapsuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/capsule/api")
@Controller
@Tag(name = "时间胶囊api")
public class CapsuleAPI {
    @Autowired
    CapsuleService capsuleService;
    @PostMapping("/publish")
    @ResponseBody
    @Operation(summary = "创建时间胶囊",description = "要求前端提供胶囊内容,创建用户,胶囊过期时间,将胶囊储存到服务器中,由于每10分钟检查一次是否过期,尽量设置为30的倍数")
    Result<Capsule> publish(@Parameter(description = "要求提供胶囊内容,胶囊过期时间(localdatetime),创建用户") Capsule capsule,
                            @Parameter(description = "可以选择是否添加图片",required = false) MultipartFile image){
        return image==null?capsuleService.create(capsule):capsuleService.create(capsule,image);
    }
    @PostMapping("/del")
    @ResponseBody
    @Operation(summary = "删除时间胶囊",description = "根据胶囊id删除胶囊,返回值为0时删除失败")
    int del(@Parameter(description = "胶囊")String capsuleId){
        return capsuleService.del(capsuleId);
    }
    @PostMapping("/get")
    @ResponseBody
    @Operation(summary = "查看时间胶囊",description = "根据胶囊id查看胶囊")
    Result<Capsule> get(@Parameter(description = "要求提供胶囊id") String capsuleId){
        return capsuleService.get(capsuleId);
    }
}
