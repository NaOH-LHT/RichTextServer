package com.example.login.service.impl;

import com.example.login.entity.EditRight;
import com.example.login.entity.KnowledgeBase;
import com.example.login.entity.KnowledgeBaseDTO;
import com.example.login.entity.User;
import com.example.login.repository.EditRightRepository;
import com.example.login.repository.KBRepository;
import com.example.login.repository.UserRepository;
import com.example.login.service.KBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KBServiceImpl implements KBService {

    @Autowired
    private KBRepository kbRepository;

    @Autowired
    private UserRepository userRespository;

    @Autowired
    private EditRightRepository editRightRepository;

    //获取知识库列表
    @Override
    public Map<String, Object> getKnowledgeBaseList(String userName) {
        System.out.println("=== 收到获取知识库列表请求 ===");
        System.out.println("请求的用户名为：" + userName);

        // 查找用户 ID
        Long userId = userRespository.findIdByUsername(userName).getUserId();
        System.out.println("对应的用户ID为：" + userId);

        // 根据 userId 查找所有的 EditRight
        List<EditRight> editRights = editRightRepository.findByUserId(userId);

        // 提取出 EditRight 中的每个 kbId
        Set<Long> kbIds = new HashSet<>();
        for (EditRight editRight : editRights) {
            kbIds.add(editRight.getKbId());
        }

        // 根据 kbIds 查找对应的 KnowledgeBase，并设置 userName
        List<KnowledgeBase> kbList = new ArrayList<>();
        for (Long kbId : kbIds) {
            KnowledgeBase kb = kbRepository.findByKbId(kbId);
            //查找nick name
            String nickname = userRespository.findById(kb.getUserId())
                    .map(User::getNickname)
                    .orElse("default_nickname");

            kb.setUserName(nickname);
            kbList.add(kb);
        }

        System.out.println("知识库列表：" + kbList);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "加载成功");
        result.put("data", kbList);
        return result;
    }

    //添加知识库
    @Override
    public Map<String, Object> addKnowledgeBase(KnowledgeBaseDTO dto) {
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();

        String owner = dto.getOwner();
        String name = dto.getName();
        String date = dto.getDate();
        List<String> editors = dto.getEditors();

        // 1. 获取用户ID
        User user = userRespository.findByNickname(owner);
        if (user == null) {
            System.out.println("无法找到用户: " + owner);
            result.put("success", false);
            result.put("message", "无法找到用户");
            return result;
        }
        Long userId = user.getUserId();
        System.out.println("用户ID: " + userId);

        // 2. 插入知识库
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.setKbName(name);
        knowledgeBase.setUserName(owner);
        knowledgeBase.setCreateTime(date);
        knowledgeBase.setAccessTime(date);
        knowledgeBase.setUserId(userId);

        kbRepository.save(knowledgeBase);
        Long kbId = knowledgeBase.getKbId();
        System.out.println("已经成功添加知识库，ID为: " + kbId);

        // 3. 添加创建者编辑权限
        EditRight editRight = new EditRight();
        editRight.setUserId(userId);
        editRight.setKbId(kbId);
        editRightRepository.save(editRight);

        // 4. 添加其他编辑者权限
        for (String editor : editors) {
            // 如果当前处理的是创建者自己，跳过重复添加权限
            if (editor.equals(owner)) {
                continue;
            }

            User editorUser = userRespository.findByNickname(editor);
            if (editorUser == null) {
                System.out.println("无法找到用户: " + editor);
                continue;
            }
            Long editorUserId = editorUser.getUserId();

            EditRight editorRight = new EditRight();
            editorRight.setUserId(editorUserId);
            editorRight.setKbId(kbId);
            editRightRepository.save(editorRight);
        }
        System.out.println("所有编辑者权限已添加");

        result.put("success", true);
        result.put("message", "知识库添加成功");
        result.put("kbId", kbId);
        return result;
    }

    @Override
    public Map<String, Object> deleteKnowledgeBase(String name) {
        System.out.println("=== 删除知识库 ===");
        System.out.println("要删除的知识库名称为：" + name);

        // 1. 根据知识库名称查找对应的 KnowledgeBase 对象
        KnowledgeBase knowledgeBase = kbRepository.findByKbName(name);
        if (knowledgeBase == null) {
            System.out.println("未找到名为 " + name + " 的知识库");
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "未找到对应的知识库");
            return result;
        }

        Long kbId = knowledgeBase.getKbId();
        System.out.println("找到知识库 ID: " + kbId);

        // 2. 删除 edit_right 表中与该 kb_id 相关的记录
        List<EditRight> editRights = editRightRepository.findByKbId(kbId);
        if (!editRights.isEmpty()) {
            editRightRepository.deleteAll(editRights);
            System.out.println("已删除 " + editRights.size() + " 条编辑权限记录");
        }

        // 3. 删除知识库
        kbRepository.delete(knowledgeBase);
        System.out.println("知识库 " + name + " 已成功删除");

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "知识库删除成功");
        return result;
    }

    @Override
    public Map<String, Object> renameKnowledgeBase(String oldName, String newName) {
        System.out.println("=== 重命名知识库 ===");
        System.out.println("旧名称：" + oldName + "，新名称：" + newName);

        // 1. 根据旧名称查找知识库
        KnowledgeBase knowledgeBase = kbRepository.findByKbName(oldName);
        if (knowledgeBase == null) {
            System.out.println("未找到名为 " + oldName + " 的知识库");
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "未找到对应的知识库");
            return result;
        }

        // 2. 检查新名称是否已存在
        KnowledgeBase existingKB = kbRepository.findByKbName(newName);
        if (existingKB != null) {
            System.out.println("名称 " + newName + " 已被占用");
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "新名称已被占用");
            return result;
        }

        // 3. 更新知识库名称
        knowledgeBase.setKbName(newName);
        kbRepository.save(knowledgeBase);

        System.out.println("知识库名称已从 " + oldName + " 修改为 " + newName);

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "知识库重命名成功");
        return result;
    }

    @Override
    public Map<String, Object> queryKnowledgeBase(String name, String owner, String startDate, String endDate) {
        System.out.println("=== 收到查询知识库请求 ===");
        System.out.println("查询条件：名称=" + name + ", 拥有者=" + owner + ", 起始时间=" + startDate + ", 结束时间=" + endDate);

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();

        // 根据参数构建查询条件
        List<KnowledgeBase> kbList = new ArrayList<>();

        // 查询所有知识库，后续可根据条件过滤（模拟复杂查询）
        if (name != null && !name.isEmpty()) {
            // 使用模糊查询代替精确查询
            kbList = kbRepository.findByKbNameContaining(name);
        } else {
            kbList = kbRepository.findAll();
        }

        // 过滤拥有者并设置user_name
        if (owner != null && !owner.isEmpty()) {
            User user = userRespository.findByNickname(owner);
            if (user != null) {
                Long userId = user.getUserId();

                // 先清理不符合条件的条目
                kbList.removeIf(kb -> !kb.getUserId().equals(userId));

                // 通过用户ID查询对应的知识库列表
                List<KnowledgeBase> kbListByUserId = kbRepository.findByUserId(userId);

                // 清空现有列表并添加准确的数据
                kbList.clear();
                kbList.addAll(kbListByUserId);
            }
        }

        // 时间范围过滤（根据创建时间）
        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            kbList.removeIf(kb -> kb.getCreateTime().compareTo(startDate) < 0 || kb.getCreateTime().compareTo(endDate) > 0);
        }

        // 为每个知识库设置user_name
        for (KnowledgeBase kb : kbList) {
            String nickname = userRespository.findById(kb.getUserId())

                    .map(User::getNickname)
                    .orElse("default_nickname");

            kb.setUserName(nickname);
        }

        System.out.println("匹配的知识库数量：" + kbList.size());

        result.put("success", true);
        result.put("message", "查询成功");
        result.put("data", kbList);
        return result;
    }

    @Override
    public Map<String, Object> queryRightKnowledgeBase(Long userId) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("=== 收到查询权限知识库请求 ===");
        // 构造返回结果
        List<EditRight> rightList = editRightRepository.findByUserId(userId);
        List<KnowledgeBase> kbList = new ArrayList<>();
        for(EditRight editRight : rightList) {
            kbList.add(kbRepository.findByKbId(editRight.getKbId()));
        }
        result.put("success", true);
        result.put("message", "查询成功");
        result.put("data", kbList);
        return result;
    }
}
