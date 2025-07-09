package com.rabbiter.bms.controller;

import com.rabbiter.bms.model.DeviceType;
import com.rabbiter.bms.service.DeviceTypeService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/deviceType")
public class DeviceTypeController {

    @Autowired
    DeviceTypeService deviceTypeService;

    // 获得数量
    @GetMapping(value = "/getCount")
    public Integer getCount(){
        return deviceTypeService.getCount();
    }

    // 查询所有类型
    @GetMapping(value = {"/queryDeviceTypes", "/reader/queryDeviceTypes"})
    public List<DeviceType> queryDeviceTypes(){
        return deviceTypeService.queryDeviceTypes();
    }

    // 分页查询设备类型 params: {page, limit, deviceTypeName}
    @GetMapping(value = "/queryDeviceTypesByPage")
    public Map<String, Object> queryDeviceTypesByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = deviceTypeService.getSearchCount(params);
        List<DeviceType> deviceTypes = deviceTypeService.searchDeviceTypesByPage(params);
        return MyResult.getListResultMap(0, "success", count, deviceTypes);
    }

    // 添加类型
    @PostMapping(value = "/addDeviceType")
    public Integer addDeviceType(@RequestBody DeviceType deviceType){
        return deviceTypeService.addDeviceType(deviceType);
    }

    // 删除类型
    @DeleteMapping(value = "/deleteDeviceType")
    public Integer deleteDeviceType(@RequestBody DeviceType deviceType){
        return deviceTypeService.deleteDeviceType(deviceType);
    }

    // 删除一些类型
    @DeleteMapping(value = "/deleteDeviceTypes")
    public Integer deleteDeviceTypes(@RequestBody List<DeviceType> deviceTypes){
        return deviceTypeService.deleteDeviceTypes(deviceTypes);
    }

    // 更新类型
    @PutMapping(value = "/updateDeviceType")
    public Integer updateDeviceType(@RequestBody DeviceType deviceType){
        return deviceTypeService.updateDeviceType(deviceType);
    }
}
