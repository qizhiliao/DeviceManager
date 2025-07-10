package com.rabbiter.bms.service;

import com.rabbiter.bms.pojo.DeviceType;

import java.util.List;
import java.util.Map;

public interface DeviceTypeService {
    Integer getCount();

    Integer getSearchCount(Map<String, Object> params);

    List<DeviceType> searchDeviceTypesByPage(Map<String, Object> params);

    Integer addDeviceType(DeviceType deviceType);

    Integer deleteDeviceType(DeviceType deviceType);

    Integer deleteDeviceTypes(List<DeviceType> deviceTypes);

    Integer updateDeviceType(DeviceType deviceType);

    List<DeviceType> queryDeviceTypes();

}
