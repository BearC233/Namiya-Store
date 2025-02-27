package com.namiya.store.project.dataobject;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.namiya.store.project.model.User;

import java.time.LocalDateTime;

public class UserDO {
    private String userName;
    private String userId;
    private String pwd;
    private String avatar;
    private String userEmail;
    private String userMedal;
    private int kindness;
    private int userType;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;
    public UserDO(){}
public UserDO(User user){
    this.userId=user.getUserId();
    this.avatar=user.getAvatar();
    this.userEmail=user.getUserEmail();
    this.userMedal=user.getUserMedal();
    this.kindness=user.getKindness();
    this.userType=user.getUserType();
    this.pwd=user.getPwd();
    this.gmtCreated=user.getGmtCreated();
    this.gmtModified=user.getGmtModified();
    this.userName=user.getUserName();
}

    public User toModel(){
    User user = new User();
    user.setAvatar(this.avatar);
    user.setUserEmail(this.userEmail);
    user.setUserMedal(this.userMedal);
    user.setKindness(this.kindness);
    user.setUserType(this.userType);
    user.setPwd(this.pwd);
    user.setUserId(this.userId);
    user.setUserName(this.userName);
    user.setGmtCreated(this.gmtCreated);
    user.setGmtModified(this.gmtModified);
    return user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMedal() {
        return userMedal;
    }

    public void setUserMedal(String userMedal) {
        this.userMedal = userMedal;
    }

    public int getKindness() {
        return kindness;
    }

    public void setKindness(int kindness) {
        this.kindness = kindness;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
}
