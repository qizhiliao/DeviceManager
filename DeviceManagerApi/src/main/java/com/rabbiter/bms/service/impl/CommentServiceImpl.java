package com.rabbiter.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.mapper.CommentMapper;
import com.rabbiter.bms.mapper.DeviceInfoMapper;
import com.rabbiter.bms.mapper.UserMapper;
import com.rabbiter.bms.pojo.Borrow;
import com.rabbiter.bms.pojo.Comment;
import com.rabbiter.bms.pojo.DeviceInfo;
import com.rabbiter.bms.pojo.User;
import com.rabbiter.bms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    DeviceInfoMapper deviceInfoMapper;
    @Autowired
    UserMapper userMapper;

    public Integer getCount(){
        return commentMapper.selectCount(null);
    }
    public List<Comment> selectAll(){
        return commentMapper.selectList(null);
    }
    public Integer getSearchCount(Map<String, Object> params) {
        QueryWrapper<Comment> queryWrapper = buildQueryWrapper(params);
        return commentMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer deleteComment(Comment comment) {
        int count = 0;
        try {
            // 使用 MyBatis-Plus 的 deleteById 方法根据 ID 删除记录
            count = commentMapper.deleteById(comment.getCommentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Integer deleteComments(List<Comment> comments) {
        int count = 0;
        for(Comment comment : comments) {
            count += deleteComment(comment);
        }
        return count;
    }

    public List<Comment> searchCommentsByPage(Map<String, Object> params) {
        int page = params.containsKey("page") ? Integer.parseInt((String) params.get("page")) : 1;
        int size = params.containsKey("limit") ? Integer.parseInt((String) params.get("limit")) : 10;
        Page<Comment> pageParam = new Page<>(page, size);
        QueryWrapper<Comment> queryWrapper = buildQueryWrapper(params);
        IPage<Comment> commentPage = commentMapper.selectPage(pageParam, queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        for(Comment comment : comments) {
            //通过borrowId查询deviceId和userId
            Borrow borrow = borrowMapper.selectById(comment.getBorrowId());
            if (borrow!= null) {
                int deviceId = borrow.getDeviceId();
                int userId = borrow.getUserId();
                //通过deviceId查询设备名称
                DeviceInfo deviceInfo = deviceInfoMapper.selectById(deviceId);
                if (deviceInfo!= null) {
                    comment.setDeviceName(deviceInfo.getDeviceName());
                }
                User user = userMapper.selectById(userId);
                if (user!= null) {
                    comment.setUserName(user.getUserName());
                }
            }
        }
        return comments;


    }


    private QueryWrapper<Comment> buildQueryWrapper(Map<String, Object> params) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        //根据设备名称查询borrowid
        if (params.containsKey("deviceName") && params.get("deviceName")!= null) {
            QueryWrapper<DeviceInfo> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("deviceName", params.get("deviceName"));
            List<DeviceInfo> deviceInfos = deviceInfoMapper.selectList(queryWrapper3);
            if (deviceInfos!= null) {
                List<Integer> deviceIds= deviceInfos.stream().map(DeviceInfo::getDeviceId).collect(Collectors.toList());
                QueryWrapper<Borrow> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.in("deviceId", deviceIds);
                List<Borrow> borrows = borrowMapper.selectList(queryWrapper1);
                if (borrows != null) {
                    List<Integer> borrowIds= borrows.stream().map(Borrow::getBorrowid).collect(Collectors.toList());
                    queryWrapper.in("borrowId", borrowIds);
                }
            }


            return queryWrapper;

        }
        // 可根据实际需求添加更多查询条件
        return queryWrapper;
    }
    public Integer addComment(Comment comment) {
        return commentMapper.insert(comment);
    }


}
