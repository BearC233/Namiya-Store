package com.namiya.store.project.api;

import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/api")
public class UserAPI {
    @Autowired
    UserService userService;
    @PostMapping("/sign")
    @ResponseBody
    Result<User> sign(String userId){
        return userService.signIn(userId);
    }
}
