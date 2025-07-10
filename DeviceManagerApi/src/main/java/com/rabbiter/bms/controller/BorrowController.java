package com.rabbiter.bms.controller;

import com.rabbiter.bms.exception.NotEnoughException;
import com.rabbiter.bms.exception.OperationFailureException;
import com.rabbiter.bms.model.Borrow;
import com.rabbiter.bms.service.DeviceInfoService;
import com.rabbiter.bms.service.BorrowService;
import com.rabbiter.bms.utils.MyResult;
import com.rabbiter.bms.utils.MyUtils;
import com.rabbiter.bms.model.DeviceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;
    @Autowired
    DeviceInfoService deviceInfoService;

    // 分页查询借用 params: {page, limit, userId, deviceId}
    @RequestMapping(value = "/queryBorrowsByPage")
    public Map<String, Object> queryBorrowsByPage(@RequestParam Map<String, Object> params){
        MyUtils.parsePageParams(params);
        int count = borrowService.getSearchCount(params);
        List<Borrow> borrows = borrowService.searchBorrowsByPage(params);
        return MyResult.getListResultMap(0, "success", count, borrows);
    }

    // 添加借用
    @RequestMapping(value = "/addBorrow")
    public Integer addBorrow(@RequestBody Borrow borrow){
        return borrowService.addBorrow(borrow);
    }

    // 获得数量
    @RequestMapping(value = "/getCount")
    public Integer getCount(){
        return borrowService.getCount();
    }

    // 删除借用
    @RequestMapping(value = "/deleteBorrow")
    public Integer deleteBorrow(@RequestBody Borrow borrow){
        return borrowService.deleteBorrow(borrow);
    }

    // 删除一些借用
    @RequestMapping(value = "/deleteBorrows")
    public Integer deleteBorrows(@RequestBody List<Borrow> borrows){
        return borrowService.deleteBorrows(borrows);
    }

    // 更新借用
    @RequestMapping(value = "/updateBorrow")
    public Integer updateBorrow(@RequestBody Borrow borrow){
        return borrowService.updateBorrow(borrow);
    }

    // 借设备
    @RequestMapping(value = {"/borrowDevice", "/reader/borrowDevice"})
    @Transactional
    public Integer borrowDevice(Integer userId, Integer deviceId){
        try{
            // 查询该设备的情况
            DeviceInfo theDevice = deviceInfoService.queryDeviceInfoById(deviceId);

            if(theDevice == null) {  // 设备不存在
                throw new NullPointerException("设备" + deviceId + "不存在");
            } else if(theDevice.getIsBorrowed() == 1) {  // 已经被借
                throw new NotEnoughException("设备" + deviceId + "库存不足（已经被借走）");
            }

            // 更新设备表的isBorrowed
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setIsBorrowed((byte) 1);
            Integer res2 = deviceInfoService.updateDeviceInfo(deviceInfo);
            if(res2 == 0) throw new OperationFailureException("设备" + deviceId + "更新被借信息失败");

            // 添加一条记录到borrow表
            Borrow borrow = new Borrow();
            borrow.setUserId(userId);
            borrow.setDeviceId(deviceId);
            borrow.setBorrowtime(new Date(System.currentTimeMillis()));
            Integer res1 = borrowService.addBorrow2(borrow);
            if(res1 == 0) throw new OperationFailureException("设备" + deviceId + "添加借用记录失败");

        } catch (Exception e) {
            System.out.println("发生异常，进行手动回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    // 还设备
    @RequestMapping(value = {"/returnDevice", "/reader/returnDevice"})
    @Transactional
    public Integer returnDevice(Integer borrowid, Integer deviceId){
        try {
            // 查询该设备的情况
            DeviceInfo theDevice = deviceInfoService.queryDeviceInfoById(deviceId);
            // 查询借设备的情况
            Borrow theBorrow = borrowService.queryBorrowsById(borrowid);

            if(theDevice == null) {  // 设备不存在
                throw new NullPointerException("设备" + deviceId + "不存在");
            } else if(theBorrow == null) {   //结束记录不存在
                throw new NullPointerException("借设备记录" + deviceId + "不存在");
            } else if(theBorrow.getReturntime() != null) {  // 已经还过设备
                throw new NotEnoughException("设备" + deviceId + "已经还过了");
            }

            // 更新设备表的isBorrowed
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(deviceId);
            deviceInfo.setIsBorrowed((byte) 0);
            Integer res2 = deviceInfoService.updateDeviceInfo(deviceInfo);
            if(res2 == 0) throw new OperationFailureException("设备" + deviceId + "更新被借信息失败");

            // 更新Borrow表，更新结束时间
            Borrow borrow = new Borrow();
            borrow.setBorrowid(borrowid);
            borrow.setReturntime(new Date(System.currentTimeMillis()));
            Integer res1 = borrowService.updateBorrow2(borrow);
            if(res1 == 0) throw new OperationFailureException("设备" + deviceId + "更新借用记录失败");

        } catch (Exception e) {
            System.out.println("发生异常，进行手动回滚");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
