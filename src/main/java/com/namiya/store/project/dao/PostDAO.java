package com.namiya.store.project.dao;

import com.namiya.store.project.dataobject.PostDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostDAO {
    List<PostDO> findAll();

    int insert(PostDO postDO);

    int update(PostDO postDO);

    int delete(@Param("postId") String postId);


    List<PostDO> findByTitle(String title);
    PostDO findById(String postId);
    List<PostDO> findByUser(String userId);
}
