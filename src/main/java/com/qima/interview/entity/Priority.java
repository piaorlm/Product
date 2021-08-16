package com.qima.interview.entity;

import com.qima.interview.constant.PriorityType;
import com.qima.interview.constant.SortDirection;

import lombok.Data;

@Data
public class Priority {
    private Integer rank;
    private PriorityType type;
    private String source;
    private SortDirection dir;
}
