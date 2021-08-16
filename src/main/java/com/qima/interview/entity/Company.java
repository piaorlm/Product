package com.qima.interview.entity;

import lombok.Data;

@Data
public class Company {
    private String id;
    private String name;
    private Company parent;
}
