package com.dci.mapper;

import com.dci.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void add(User user);
    void del(User user);
    User query(User user);



}
