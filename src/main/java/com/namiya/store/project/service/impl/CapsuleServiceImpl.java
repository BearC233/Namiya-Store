package com.namiya.store.project.service.impl;

import com.namiya.store.project.dao.CapsuleDAO;
import com.namiya.store.project.dataobject.CapsuleDO;
import com.namiya.store.project.model.Capsule;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.Status;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.CapsuleService;
import com.namiya.store.project.service.FileStorageService;
import com.namiya.store.project.service.NotificationService;
import com.namiya.store.project.service.UserService;
import com.namiya.store.project.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CapsuleServiceImpl implements CapsuleService {
    @Autowired
    CapsuleDAO capsuleDAO;
    @Autowired
    UserService userService;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    NotificationService notificationService;
    @Override
    @Scheduled(cron = "0 0/10 * * * ?")//10分钟检查一次
    public void checkExpiredCapsules() {
        List<CapsuleDO> capsules = capsuleDAO.findBeforeNowAndActive(LocalDateTime.now());
        capsules.forEach(capsuleDO -> {
            User data = userService.findById(capsuleDO.getCapsuleUser()).getData();
            if(data==null) {
                return;
            }
            capsuleDO.setStatus(Status.NOTIFIED);
            notificationService.sendNotification(data.getUserEmail(),data.getUserName()+":您的时间胶囊已到期!");
            capsuleDAO.update(capsuleDO);
        });

    }

    @Override
    public Result<Capsule> create(Capsule capsule) {
        Result<Capsule> result = new Result<>();
        if(capsule.getCapsuleContent()==null) {
            result.setSuccess(false);
            result.setCode("202");
            result.setMessage("帖子内容为空!");
            return result;
        }
        if(capsule.getCapsuleEnd().isBefore(LocalDateTime.now()) ){
            result.setSuccess(false);
            result.setCode("203");
            return result;
        }
        if(capsule.getCapsuleUser()==null){
            result.setSuccess(false);
            result.setCode("202");
            result.setMessage("帖子用户为空!");
            return result;
        }
        capsule.setCapsuleId(UUIDGenerator.Generator(capsule.getCapsuleUser()));
        boolean b = capsuleDAO.insert(new CapsuleDO(capsule)) > 0;
        if(b) {result.setSuccess(true);result.setData(capsule); return result;}
        else{
            result.setCode("201");
            result.setSuccess(false);
            result.setMessage("上传失败!请稍后再试");
            return result;
        }
    }

    @Override
    public Result<Capsule> create(Capsule capsule, MultipartFile image) {
        Result<Capsule> result = new Result<>();
        if(!isImage(image)){
            result.setSuccess(false);
            result.setCode("205");
            result.setMessage("不支持非图片格式!");
            return result;
        }
        if(capsule.getCapsuleContent()==null) {
            result.setSuccess(false);
            result.setCode("202");
            result.setMessage("帖子内容为空!");
            return result;
        }
        if(capsule.getCapsuleEnd().isBefore(LocalDateTime.now()) ){
            result.setSuccess(false);
            result.setCode("203");
            return result;
        }
        if(capsule.getCapsuleUser()==null){
            result.setSuccess(false);
            result.setCode("202");
            result.setMessage("帖子用户为空!");
            return result;
        }
        capsule.setCapsuleId(UUIDGenerator.Generator(capsule.getCapsuleUser()));
        try {
            capsule.setImg(fileStorageService.storeFile(image));
        } catch (IOException e) {
            result.setSuccess(false);
            result.setCode("206");
            result.setMessage("图片上传失败!");
            return result;
        }
        boolean b = capsuleDAO.insert(new CapsuleDO(capsule)) > 0;
        if(b) {result.setSuccess(true);result.setData(capsule); return result;}
        else{
            result.setCode("201");
            result.setSuccess(false);
            result.setMessage("上传失败!请稍后再试");
            return result;
        }
    }

    @Override
    public int del(String capsuleId) {
        return capsuleDAO.delete(capsuleId);
    }

    @Override
    public Result<Capsule> get(String capsuleId) {
        Result<Capsule> result = new Result<>();
        if(capsuleId==null) {
            result.setSuccess(false);
            result.setCode("202");
            result.setMessage("请输入id");
            return result;
        }
        CapsuleDO capsuleDO = capsuleDAO.get(capsuleId);
        if(capsuleDO==null){
            result.setSuccess(false);
            result.setCode("203");
            result.setMessage("胶囊不存在");
            return result;
        }
        else{
            result.setSuccess(true);
            result.setData(capsuleDO.toModel());
            return result;
        }

    }
    private boolean isImage(MultipartFile file){
        String contentType=file.getContentType();
        return contentType!=null&&contentType.startsWith("image/");
    }
}
