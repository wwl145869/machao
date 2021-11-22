package com.dci.service;

import com.dci.mapper.DietRecordsMapper;
import com.dci.pojo.DietRecords;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class DietRecordsService implements DietRecordsInterface {

    @Autowired
    DietRecordsMapper dietRecordsMapper;

    @Transactional
    public int write(DietRecords dietRecords) {
        int ret=0;
        try {
            dietRecordsMapper.write(dietRecords);
            log.info("----DietRecordsService.write()--执行成功------");
            ret=0;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("----DietRecordsService.write()--执行失败------");
            ret=1;
        } finally {
            return ret;
        }
    }
    public List queryAll() {
        List<DietRecords> dietRecords = dietRecordsMapper.queryAll();

        return dietRecords;
    }
}
