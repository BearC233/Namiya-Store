package com.namiya.store.project.service.impl;

import com.namiya.store.project.dao.UserDAO;
import com.namiya.store.project.dataobject.UserDO;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.UserService;
import com.namiya.store.project.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RedisTemplate redisTemplate=new RedisTemplate();
    @Override
    public Result<User> signIn(String userId) {
        Result<User> result=new Result<>();
        //首先检测缓存中有没有该用户信息
        User user = (User) redisTemplate.opsForValue().get(userId);
        if(user==null){
            //缓存中没有，检查数据库中有没有
            UserDO userDO = userDAO.findById(userId);
            if(userDO==null){
                result.setCode("401");
                result.setSuccess(false);
                return result;
            }
            //缓存中没有，数据库中有：
            user = userDO.toModel();
        }
        LocalDateTime now=LocalDateTime.now();
        String month=now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key="sign"+userId+month;
        int dayOfMonth=now.getDayOfMonth();
        //先查看是否有签到记录：
        if (redisTemplate.opsForValue().getBit(key,dayOfMonth-1)) {
            result.setCode("402");
            result.setSuccess(false);
            return result;
        }
        redisTemplate.opsForValue().setBit(key,dayOfMonth-1,true);
        user.setKindness(user.getKindness()+3);//签到一次给多少善值
        result.setSuccess(true);
        result.setMessage("签到成功!");
        userDAO.update(new UserDO(user));//更新数据库和缓存
        redisTemplate.opsForValue().set("user"+userId,user);
        result.setData(user);
        return result;
    }

    @Override
    public Result<User> register(User user) {
        Result<User> result=new Result<>();
        UserDO userDO=new UserDO();
        if(user.getUserName()==null){
            if(user.getUserEmail()==null){
                result.setSuccess(false);
                result.setCode("403");
                return result;
            }
            //邮箱注册
            if(userDAO.findByEmail(user.getUserEmail())!=null){
                result.setSuccess(false);
                result.setCode("406");
                result.setMessage("邮箱已注册");
                return result;
            }
            userDO.setUserEmail(user.getUserEmail());
            userDO.setUserId(UUIDGenerator.Generator(""));
            userDO.setUserName("UU"+userDO.getUserId());
        }
        else{
            //名称注册

            if(userDAO.findByName(user.getUserName())!=null){
                result.setSuccess(false);
                result.setCode("406");
                result.setMessage("用户名已注册");
                return result;
            }
            userDO.setUserName(user.getUserName());
            userDO.setUserId(UUIDGenerator.Generator(user.getUserName()));
        }
        if(user.getPwd()==null){
            result.setSuccess(false);
            result.setCode("404");
            return result;
        }
        userDO.setPwd(user.getPwd());
        int insert = userDAO.insert(userDO);
        if(insert>0){
            redisTemplate.opsForValue().set("user"+userDO.getUserId(),userDO.toModel());
            result.setSuccess(true);
            result.setData(userDO.toModel());
            result.setMessage("注册成功");
        }
        else{
            result.setCode("405");
            result.setSuccess(false);

        }
        return result;

    }

    @Override
    public Result<User> login(User user) {

        Result<User> result=new Result<>();
        UserDO userDO = userDAO.findByEmail(user.getUserEmail());
        if(userDO ==null){
            userDO = userDAO.findByName(user.getUserName());
            if(userDO ==null){
                result.setCode("401");
                result.setSuccess(false);
                return result;
            }
        }
        if(!Objects.equals(userDO.getPwd(), user.getPwd())){
            result.setSuccess(false);
            result.setCode("407");
            result.setMessage("密码或用户名不正确！");
            return result;
        }
        result.setData(userDO.toModel());
        result.setSuccess(true);
        return result;
    }

    @Override
    public Result<User> findById(String userId) {
        Result<User> result = new Result<>();
        UserDO byId = userDAO.findById(userId);
        if (byId !=null) {
            result.setSuccess(true);
            result.setData(byId.toModel());
            return result;
        }
        else{
            result.setSuccess(false);
            result.setCode("401");
            return result;
        }
    }

    @Override
    public Result<User> findByName(String userName) {
        Result<User> result = new Result<>();
        UserDO byName = userDAO.findByName(userName);
        if (byName !=null) {
            result.setSuccess(true);
            result.setData(byName.toModel());
            return result;
        }
        else{
            result.setSuccess(false);
            result.setCode("401");
            return result;
        }
    }

    @Override
    public Result<List<User>> getByName(String userName) {
        Result<List<User>> result = new Result<>();
        List<UserDO> byName = userDAO.getByName(userName);
        if (!byName.isEmpty()) {
            result.setSuccess(true);
            result.setData(byName.stream().map(UserDO::toModel).toList());
            return result;
        }
        else{
            result.setSuccess(false);
            result.setCode("401");
            return result;
        }
    }

    @Override
    public int del(String userId) {
        return userDAO.delete(userId);
    }

    @Override
    public Result<List<User>> findAll() {
       Result<List<User>> result = new Result<>();
        List<UserDO> all = userDAO.findAll();
        if(all.isEmpty()) {
            result.setSuccess(false);
            result.setCode("404");
            result.setMessage("查询失败!请稍后再试");
        }
        List<User> list = all.stream().map(UserDO::toModel).toList();
        result.setSuccess(true);
        result.setData(list);
        return result;
    }

    @Override
    public Result<Integer> update(User user) {
        Result<Integer> result=new Result<>();
        if(user.getUserId()==null){
            result.setSuccess(false);
            result.setCode("401");
            result.setMessage("无用户id!");
            return result;
        }
        int update = userDAO.update(new UserDO(user));
        if(update==0) {

            result.setSuccess(false);
            result.setMessage("更新失败!");
            result.setCode("405");
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
