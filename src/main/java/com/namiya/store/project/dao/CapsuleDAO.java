package com.namiya.store.project.dao;

import com.namiya.store.project.dataobject.CapsuleDO;
import com.namiya.store.project.model.Status;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
@Mapper
public interface CapsuleDAO {
    List<CapsuleDO> findAll();

    int insert(CapsuleDO capsuleDO);

    int update(CapsuleDO capsuleDO);

    int delete(@Param("capsuleId") String capsuleId);

    List<CapsuleDO> findBeforeNowAndActive(LocalDateTime now);
    CapsuleDO get(String capsuleId);
}
