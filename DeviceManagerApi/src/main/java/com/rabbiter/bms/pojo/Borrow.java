package com.rabbiter.bms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("borrow")
public class Borrow {
    @TableId(value = "borrowId",type= IdType.AUTO)
    private Integer borrowid;
//  注释注释
    @TableField(exist = false)
    private String userName;
    @TableField(value = "userId")
    private Integer userId;
    @TableField(value = "deviceId")
    private Integer deviceId;

    @TableField(exist = false)
    private String deviceName;
    @TableField(value = "borrowTime")
    private Date borrowtime;

    @TableField(exist = false)
    private String borrowtimestr;
    @TableField(value = "returnTime")
    private Date returntime;

    @TableField(exist = false)
    private String returntimestr;

}
