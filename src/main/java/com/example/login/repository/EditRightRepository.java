package com.example.login.repository;

import com.example.login.entity.EditRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "*")
public interface EditRightRepository extends JpaRepository<EditRight, Long> {
    //根据用户id查询编辑权限
    List<EditRight> findByUserId(Long userId);

    //根据知识库id查询编辑权限
    List<EditRight> findByKbId(Long kbId);
}
