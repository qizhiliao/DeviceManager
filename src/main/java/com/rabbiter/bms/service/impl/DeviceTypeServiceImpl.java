package com.rabbiter.bms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rabbiter.bms.mapper.DeviceTypeMapper;
import com.rabbiter.bms.model.DeviceInfo;
import com.rabbiter.bms.service.DeviceTypeService;
import com.rabbiter.bms.model.DeviceType;
import org.springframework.data.convert.TypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DeviceTypeServiceImpl implements DeviceTypeService {

    @Resource
    private DeviceTypeMapper deviceTypeMapper;

    @Override
    public Integer getCount() {
        return deviceTypeMapper.selectCount(null);
    }

    @Override
    public Integer getSearchCount(Map<String, Object> params) {
        QueryWrapper<DeviceType> queryWrapper = buildQueryWrapper(params);
        // 使用 MyBatis-Plus 的 selectCount 方法根据条件查询记录数量
        return deviceTypeMapper.selectCount(queryWrapper);
    }

    @Override
    public List<DeviceType> searchDeviceTypesByPage(Map<String, Object> params) {
        // 从参数中获取页码和每页数量，若不存在则使用默认值
        int page = params.containsKey("page") ? Integer.parseInt((String) params.get("page")) : 1;
        int size = params.containsKey("limit") ? Integer.parseInt((String) params.get("limit")) : 10;
        // 创建分页对象
        Page<DeviceType> pageParam = new Page<>(page, size);
        // 构建查询条件
        QueryWrapper<DeviceType> queryWrapper = buildQueryWrapper(params);
        // 执行分页查询
        IPage<DeviceType> deviceTypePage = deviceTypeMapper.selectPage(pageParam, queryWrapper);
        return deviceTypePage.getRecords();
    }

    @Override
    public Integer addDeviceType(DeviceType deviceType) {
        return deviceTypeMapper.insert(deviceType);
    }

    @Override
    public Integer deleteDeviceType(DeviceType deviceType) {
        return deviceTypeMapper.deleteById(deviceType.getDeviceTypeId());
    }

    @Override
    public Integer deleteDeviceTypes(List<DeviceType> deviceTypes) {
        int count = 0;
        for(DeviceType deviceType : deviceTypes) {
            count += deleteDeviceType(deviceType);
        }
        return count;
    }

    @Override
    public Integer updateDeviceType(DeviceType deviceType) {
        return deviceTypeMapper.updateById(deviceType);
    }

    @Override
    public List<DeviceType> queryDeviceTypes() {
        return deviceTypeMapper.selectList(null);
    }

    private QueryWrapper<DeviceType> buildQueryWrapper(Map<String, Object> params) {
        QueryWrapper<DeviceType> queryWrapper = new QueryWrapper<>();
        // 示例查询条件，可根据实际情况修改
        if (params.containsKey("deviceTypeName") && params.get("deviceTypeName") != null) {
            queryWrapper.eq("device_type_name", params.get("deviceTypeName"));
        }
        // 可添加更多查询条件
        return queryWrapper;
    }
}
