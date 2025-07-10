package com.rabbiter.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.bms.mapper.UserMapper;
import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public User login(User user) {
        // 创建 QueryWrapper 对象
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq("userName", user.getUserName())
                .eq("userPassword", user.getUserPassword())
                .eq("isAdmin", user.getIsAdmin());
        // 执行查询，返回单个用户对象
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveUser(String token, User user) {
        // 设置redisTemplate对象key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // key是token，value是用户保存到redis中，超时时间1小时
        redisTemplate.opsForValue().set(token, user, 1, TimeUnit.HOURS);
    }

    @Override
    public User getUser(String token) {
        // 根据token得到user
        return (User) redisTemplate.opsForValue().get(token);
    }

    @Override
    public void removeUser(String token) {
        // 移除token
        redisTemplate.delete(token);
    }

    @Override
    public Integer register(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName", userName);
        User tmp = userMapper.selectOne(queryWrapper);
        if(tmp != null) return 0;  // 账号重复

        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(password);
        user.setIsAdmin((byte)0);
        return userMapper.insert(user);
    }

    @Override
    public void setPassword(Integer id, String password) {
        User user = new User();
        user.setUserId(id);
        user.setUserPassword(password);
        userMapper.updateById(user);
    }

    @Override
    public Integer getCount() {
        return userMapper.selectCount(null);
    }

    @Override
    public List<User> queryUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public int getSearchCount(Map<String, Object> params) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("userName") && params.get("userName") != null) {
            queryWrapper.like("userName", params.get("userName"));
        }
        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public List<User> searchUsersByPage(Map<String, Object> params)
    {
        int page = Integer.parseInt((String) params.get("page"));
        int size = Integer.parseInt((String) params.get("limit"));
        Page<User> pageParam = new Page<>(page, size);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("userName") && params.get("userName") != null) {
            queryWrapper.like("userName", params.get("userName"));
        }

        IPage<User> userPage = userMapper.selectPage(pageParam, queryWrapper);
        return userPage.getRecords();
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public Integer deleteUser(User user) {
        if(user.getUserId() == 1) return 0;
        return userMapper.deleteById(user.getUserId());
    }

    @Override
    public Integer deleteUsers(List<User> users) {
        int count = 0;
        for(User user : users) {
            count += deleteUser(user);
        }
        return count;
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }

}
