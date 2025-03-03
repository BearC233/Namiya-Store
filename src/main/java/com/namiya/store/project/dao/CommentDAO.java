package com.namiya.store.project.dao;

import com.namiya.store.project.dataobject.CommentDO;
import com.namiya.store.project.dataobject.PostDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDAO {
    List<CommentDO> findAll();

    int insert(CommentDO commentDO);

    int update(CommentDO commentDO);

    int delete(@Param("commentId") String commentId);

    CommentDO findById(String commentId);
    List<CommentDO> findByUser(String userId);
}
