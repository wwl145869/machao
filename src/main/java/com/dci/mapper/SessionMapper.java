package com.dci.mapper;

import com.dci.pojo.Session;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SessionMapper {
    void add(Session session);
    void del(Session session);
    Session query(Session session);
}
