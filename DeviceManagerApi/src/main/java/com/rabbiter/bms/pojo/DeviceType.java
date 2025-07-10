package com.rabbiter.bms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("device_type")
public class DeviceType {
    @TableId(value="deviceTypeId",type = IdType.AUTO)
    @TableField("deviceTypeId")
    private Integer deviceTypeId;
    @TableField("deviceTypeName")
    private String deviceTypeName;
    @TableField("deviceTypeDesc")
    private String deviceTypeDesc;

}
