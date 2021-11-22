package com.dci.service;

import com.dci.pojo.DietRecords;

import java.util.List;

public interface DietRecordsInterface {
    int write(DietRecords dietRecords);
    List<DietRecords> queryAll();
}
