package com.rabbiter.bms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rabbiter.bms.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
