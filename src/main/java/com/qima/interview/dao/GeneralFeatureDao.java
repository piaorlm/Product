package com.qima.interview.dao;

import com.qima.interview.entity.GeneralFeature;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GeneralFeatureDao {
    GeneralFeature getByCode(String code);
}
