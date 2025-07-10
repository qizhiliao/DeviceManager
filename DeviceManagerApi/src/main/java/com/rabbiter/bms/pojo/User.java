package com.rabbiter.bms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("user")
public class User implements Serializable {
    @TableId(value = "userId",type = IdType.AUTO)
    @TableField("userId")
    private Integer userId;
    @TableField("userName")
    private String userName;
    @TableField("userPassword")
    private String userPassword;
    @TableField("isAdmin")
    private Byte isAdmin;

}
