package com.rabbiter.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.bms.mapper.DeviceInfoMapper;
import com.rabbiter.bms.mapper.DeviceTypeMapper;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.model.DeviceInfo;
import com.rabbiter.bms.model.DeviceType;
import com.rabbiter.bms.model.User;
import com.rabbiter.bms.service.DeviceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service// 标注为服务层组件
public class DeviceInfoServiceImpl implements DeviceInfoService {

    @Resource
    private DeviceInfoMapper deviceInfoMapper;// 注入 DeviceInfoMapper
    @Resource
    private DeviceTypeMapper deviceTypeMapper;// 注入 DeviceTypeMapper

    @Override// 实现获取设备数量的方法
    public Integer getCount() {
        return deviceInfoMapper.selectCount(null);
    }

    @Override// 实现查询所有设备信息的方法
    public List<DeviceInfo> queryDeviceInfos() {
        return deviceInfoMapper.selectList(null);
    }

    @Override// 实现根据设备ID查询设备信息的方法
    public DeviceInfo queryDeviceInfoById(Integer deviceId) {
        return deviceInfoMapper.selectById(deviceId);
    }

    @Override// 实现根据条件查询设备数量的方法
    public Integer getSearchCount(Map<String, Object> params) {
        QueryWrapper<DeviceInfo> queryWrapper = buildQueryWrapper(params);
        // 使用 MyBatis-Plus 的 selectCount 方法根据条件查询记录数量
        return deviceInfoMapper.selectCount(queryWrapper);
    }

    @Override// 实现根据条件分页查询设备信息的方法
    public List<DeviceInfo> searchDeviceInfosByPage(Map<String, Object> params) {
        int page = Integer.parseInt((String) params.get("page"));
        int size = Integer.parseInt((String) params.get("limit"));
        Page<DeviceInfo> pageParam = new Page<>(page, size);
        QueryWrapper<DeviceInfo> queryWrapper = buildQueryWrapper(params);
        IPage<DeviceInfo> deviceInfoPage = deviceInfoMapper.selectPage(pageParam, queryWrapper);
        List<DeviceInfo> deviceInfos = deviceInfoPage.getRecords();
        for(DeviceInfo deviceInfo : deviceInfos) {
            // 通过设备类型Id查询设备类型名称
            DeviceType deviceType = deviceTypeMapper.selectById(deviceInfo.getDeviceTypeId());
            if (deviceType!= null) {
                deviceInfo.setDeviceTypeName(deviceType.getDeviceTypeName());
            }
        }


        return deviceInfoPage.getRecords();
    }

    @Override// 实现添加设备信息的方法
    public Integer addDeviceInfo(DeviceInfo deviceInfo) {
        return deviceInfoMapper.insert(deviceInfo);
    }

    @Override// 实现删除设备信息的方法
    public Integer deleteDeviceInfo(DeviceInfo deviceInfo) {
        int count = 0;
        try {
            // 使用 MyBatis-Plus 的 deleteById 方法根据 ID 删除记录
            count = deviceInfoMapper.deleteById(deviceInfo.getDeviceId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override// 实现批量删除设备信息的方法
    public Integer deleteDeviceInfos(List<DeviceInfo> deviceInfos) {
        int count = 0;
        for(DeviceInfo deviceInfo : deviceInfos) {
            count += deleteDeviceInfo(deviceInfo);
        }
        return count;
    }

    @Override// 实现更新设备信息的方法
    public Integer updateDeviceInfo(DeviceInfo deviceInfo) {
        return deviceInfoMapper.updateById(deviceInfo);
    }
    // 构建查询条件的方法
    private QueryWrapper<DeviceInfo> buildQueryWrapper(Map<String, Object> params) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        // 示例查询条件，可根据实际情况修改
        if (params.containsKey("deviceName") && params.get("deviceName") != null) {
            queryWrapper.eq("deviceName", params.get("deviceName"));
        }
        if (params.containsKey("deviceAuthor") && params.get("deviceAuthor")!= null) {
            queryWrapper.eq("deviceAuthor", params.get("deviceAuthor"));
        }
        if (params.containsKey("deviceType") && params.get("deviceType") != null) {
            queryWrapper.eq("deviceType", params.get("deviceType"));
        }
        if(params.containsKey("deviceTypeId")&&params.get("deviceTypeId")!=null){
            queryWrapper.eq("deviceTypeId",params.get("deviceTypeId"));
        }
        return queryWrapper;
    }
}
