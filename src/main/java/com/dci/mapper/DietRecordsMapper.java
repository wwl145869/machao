package com.dci.mapper;

import com.dci.pojo.DietRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DietRecordsMapper {
    void write(DietRecords dietRecords);
    List<DietRecords> queryAll();
}
