package com.rabbiter.bms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
@Data
@TableName("device_info")
public class DeviceInfo {
    @TableId(value = "deviceId",type= IdType.AUTO)
    private Integer deviceId;
    @TableField("deviceName")
    private String deviceName;
    @TableField("deviceAuthor")
    private String deviceAuthor;
    @TableField("devicePrice")
    private BigDecimal devicePrice;
    @TableField("deviceTypeId")
    private Integer deviceTypeId;

    @TableField(exist = false)
    private String deviceTypeName;
    @TableField("deviceDesc")
    private String deviceDesc;
    @TableField("isBorrowed")
    private Byte isBorrowed;
    @TableField("deviceImg")
    private String deviceImg;


}
