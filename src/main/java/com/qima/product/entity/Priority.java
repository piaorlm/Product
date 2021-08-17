package com.qima.product.entity;

import com.qima.product.constant.PriorityType;
import com.qima.product.constant.SortDirection;

import lombok.Data;

@Data
public class Priority {
    private Integer rank;
    private PriorityType type;
    private String source;
    private SortDirection dir;
}
