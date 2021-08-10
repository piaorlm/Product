package com.qima.interview.service;

import com.qima.interview.dao.GeneralFeatureDao;
import com.qima.interview.entity.GeneralFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralFeatureService {
    @Autowired
    private GeneralFeatureDao generalFeatureDao;
    
    public GeneralFeature getByCode(String code) {
        return generalFeatureDao.getByCode(code);
    }
}
