package com.rabbiter.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.bms.mapper.BorrowMapper;
import com.rabbiter.bms.mapper.DeviceInfoMapper;
import com.rabbiter.bms.mapper.UserMapper;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.model.DeviceInfo;
import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public Integer getCount() {
        return borrowMapper.selectCount(null);
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        QueryWrapper<Borrow> queryWrapper = buildQueryWrapper(params);
        // 使用 MyBatis-Plus 的 selectCount 方法，根据查询条件查询记录数量
        return borrowMapper.selectCount(queryWrapper);
    }

    @Override
    public List<Borrow> searchBorrowsByPage(Map<String, Object> params) {
        // 从参数中获取页码和每页数量，若不存在则使用默认值
        int page = params.containsKey("page") ? Integer.parseInt((String) params.get("page")) : 1;
        int size = params.containsKey("limit") ? Integer.parseInt((String) params.get("limit")) : 10;
        Page<Borrow> pageParam = new Page<>(page, size);
        QueryWrapper<Borrow> queryWrapper = buildQueryWrapper(params);
        // 使用 MyBatis-Plus 的 selectPage 方法进行分页查询
        IPage<Borrow> borrowPage = borrowMapper.selectPage(pageParam, queryWrapper);
        List<Borrow> borrows = borrowPage.getRecords();
        // 添加 string 类型的时间显示
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Borrow borrow : borrows) {
            if (borrow.getBorrowtime() != null) {
                borrow.setBorrowtimestr(simpleDateFormat.format(borrow.getBorrowtime()));
            }
            if (borrow.getReturntime() != null) {
                borrow.setReturntimestr(simpleDateFormat.format(borrow.getReturntime()));
            }

            // 通过用户 ID 查询用户名
            if (borrow.getUserId() != null) {
                System.out.println("查询用户信息，userId: " + borrow.getUserId());
                User user = userMapper.selectById(borrow.getUserId());
                //System.out.println("查询用户信息，user: " + user.getUsername());
                // 检查 user 对象是否为 null
                if (user != null) {
                    borrow.setUserName(user.getUserName());
                } else {
                    // 若 user 为 null，可设置默认值
                    borrow.setUserName("未知用户");
                }
            }
            // 通过设备 ID 查询设备名
            if (borrow.getDeviceId() != null) {
                DeviceInfo device = deviceInfoMapper.selectById(borrow.getDeviceId());
                // 检查 device 对象是否为 null
                if (device != null) {
                    borrow.setDeviceName(device.getDeviceName());
                } else {
                    // 若 device 为 null，可设置默认值
                    borrow.setDeviceName("未知设备");
                }
            }


        }
        return borrows;
    }

    @Override
    public Integer addBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr()));
            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowMapper.insert(borrow);
    }

    // 不会调整时间格式的add
    @Override
    public Integer addBorrow2(Borrow borrow) {
        return borrowMapper.insert(borrow);
    }

    @Override
    public Integer deleteBorrow(Borrow borrow) {
        // 先查询有没有还设备
        Borrow borrow1 = borrowMapper.selectById(borrow.getBorrowid());
        if(borrow1.getReturntime() == null) return 0;
        return borrowMapper.deleteById(borrow.getBorrowid());
    }

    @Override
    public Integer deleteBorrows(List<Borrow> borrows) {
        int count = 0;
        for(Borrow borrow : borrows) {
            count += deleteBorrow(borrow);
        }
        return count;
    }

    @Override
    public Integer updateBorrow(Borrow borrow) {
        // 将string类型的时间重新调整
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            borrow.setBorrowtime(simpleDateFormat.parse(borrow.getBorrowtimestr()));
            borrow.setReturntime(simpleDateFormat.parse(borrow.getReturntimestr()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return borrowMapper.updateById(borrow);
    }

    // 不调整时间格式的更新
    @Override
    public Integer updateBorrow2(Borrow borrow) {
        return borrowMapper.updateById(borrow);
    }

    @Override
    public Borrow queryBorrowsById(Integer borrowid) {
        return borrowMapper.selectById(borrowid);
    }

    private QueryWrapper<Borrow> buildQueryWrapper(Map<String, Object> params) {
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        //根据设备名称查询设备id
        if (params.containsKey("deviceName") && params.get("deviceName")!= null) {
            QueryWrapper<DeviceInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("deviceName", params.get("deviceName"));
            DeviceInfo deviceInfo = deviceInfoMapper.selectOne(queryWrapper1);
            if(deviceInfo != null) {
                queryWrapper.eq("deviceId", deviceInfo.getDeviceId());
            }
        }
        // 示例：假设 params 中有 deviceId 和 userId 作为查询条件
        if (params.containsKey("deviceId") && params.get("deviceId") != null) {
            queryWrapper.eq("deviceId", params.get("deviceId"));
        }

        if(params.containsKey("userName") && params.get("userName")!= null) {
            //通过用户名查询用户id
            QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("userName", params.get("userName"));
            User user = userMapper.selectOne(queryWrapper1);
            if(user!= null) {
                queryWrapper.eq("userId", userMapper.selectOne(queryWrapper1).getUserId());
            }


        }
        if (params.containsKey("userId") && params.get("userId") != null) {
            queryWrapper.eq("userId", params.get("userId"));
        }
        // 可根据实际需求添加更多查询条件
        return queryWrapper;
    }

}
