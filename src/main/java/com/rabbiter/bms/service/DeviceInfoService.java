package com.rabbiter.bms.service;

import com.rabbiter.bms.model.DeviceInfo;

import java.util.List;
import java.util.Map;

public interface DeviceInfoService {
    Integer getCount();

    List<DeviceInfo> queryDeviceInfos();

    DeviceInfo queryDeviceInfoById(Integer deviceId);

    Integer getSearchCount(Map<String, Object> params);

    List<DeviceInfo> searchDeviceInfosByPage(Map<String, Object> params);

    Integer addDeviceInfo(DeviceInfo deviceInfo);

    Integer deleteDeviceInfo(DeviceInfo deviceInfo);

    Integer deleteDeviceInfos(List<DeviceInfo> deviceInfos);

    Integer updateDeviceInfo(DeviceInfo deviceInfo);
}
