package com.namiya.store.project.api;

import com.namiya.store.project.model.Result;
import com.namiya.store.project.model.User;
import com.namiya.store.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user/api")
@Tag(name = "用户管理")
public class UserAPI {
    @Autowired
    UserService userService;
    @PostMapping("/sign")
    @ResponseBody
    @Operation(summary ="用户签到",description="前端需给予登录后用户的id,接口返回签到的用户并更新善值+3")
    Result<User> sign(@Parameter(description = "用户ID", required = true)String userId){
        return userService.signIn(userId);
    }
    @PostMapping("/login")
    @ResponseBody
    @Operation(summary ="用户登录",description="前端根据提交的表单组建user对象,识别用户输入的是邮箱还是名称,组装到对应json字段上")
    Result<User> login(@Parameter(description = "用户登录表单", required = true)User user){
        return userService.login(user);
    }
    @PostMapping("/reg")
    @ResponseBody
    @Operation(summary ="用户注册",description="前端根据提交的表单组建user对象,识别用户输入的是邮箱还是名称,组装到对应json字段上")
    Result<User> reg(@Parameter(description = "用户注册表单", required = true)User user){
        return userService.register(user);
    }
}
