package com.qima.interview.entity;

import java.util.Date;

import lombok.Data;

@Data
public class GeneralFeature {
    private String id;
    private String name;
    private String code;
    private String type;
    private String comments;
    private Date createTime;
    private Date updateTime;
    private Boolean synchronizable;
    private String defaultValue;
}
