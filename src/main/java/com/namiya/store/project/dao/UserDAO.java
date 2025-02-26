package com.namiya.store.project.dao;


import com.namiya.store.project.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDAO {
    List<UserDO> findAll();

    int insert(UserDO userDO);

    int update(UserDO userDO);

    int delete(@Param("userId") String userId);

    int count();

    UserDO findByName(String userName);
    UserDO findById(String userId);
}
