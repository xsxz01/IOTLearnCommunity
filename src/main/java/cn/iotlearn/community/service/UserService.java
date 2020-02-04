package cn.iotlearn.community.service;

import cn.iotlearn.community.mapper.UserMapper;
import cn.iotlearn.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User userById = userMapper.findById(Integer.parseInt(user.getAccountId()));
        if (userById == null){
            // 插入新用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        }else {
            // 更新
            userById.setGmtModified(System.currentTimeMillis());
            userById.setAvatarUrl(user.getAvatarUrl());
            userById.setName(user.getName());
            userById.setToken(user.getToken());
            userMapper.updateUser(userById);
        }
    }
}
