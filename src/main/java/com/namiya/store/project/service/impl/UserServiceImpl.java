package com.namiya.store.project.service.impl;

import com.namiya.store.project.dao.UserDAO;
import com.namiya.store.project.dataobject.UserDO;
import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}
