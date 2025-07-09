package com.rabbiter.bms.web;

import com.rabbiter.bms.service.DeviceInfoService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import com.rabbiter.bms.model.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/deviceInfo")
public class DeviceInfoController {

    @Autowired
    DeviceInfoService deviceInfoService;

    // 获取设备数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return deviceInfoService.getCount();
    }

    // 查询所有设备信息
    @GetMapping(value = "/queryDeviceInfos")
    public List<DeviceInfo> queryDeviceInfos(){
        return deviceInfoService.queryDeviceInfos();
    }

    @GetMapping(value = "/queryDeviceInfosByPage")
    public Map<String, Object> queryDeviceInfosByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = deviceInfoService.getSearchCount(params);  // 获得总数
        List<DeviceInfo> deviceInfos = deviceInfoService.searchDeviceInfosByPage(params);  // 分页查询
        return MyResult.getListResultMap(0, "success", count, deviceInfos);
    }

    // 添加设备信息
    @PostMapping(value = "/addDeviceInfo")
    public Integer addDeviceInfo(@RequestBody DeviceInfo deviceInfo){
        return deviceInfoService.addDeviceInfo(deviceInfo);
    }

    // 删除设备信息
    @DeleteMapping(value = "/deleteDeviceInfo")
    public Integer deleteDeviceInfo(@RequestBody DeviceInfo deviceInfo){
        return deviceInfoService.deleteDeviceInfo(deviceInfo);
    }

    // 删除一些设备信息
    @DeleteMapping(value = "/deleteDeviceInfos")
    public Integer deletedDviceInfos(@RequestBody List<DeviceInfo> deviceInfos){
        return deviceInfoService.deleteDeviceInfos(deviceInfos);
    }

    // 更新设备信息
    @PutMapping(value = "/updateDeviceInfo")
    public Integer updateDeviceInfo(@RequestBody DeviceInfo deviceInfo){
        return deviceInfoService.updateDeviceInfo(deviceInfo);
    }
}
