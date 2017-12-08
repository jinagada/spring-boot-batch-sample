package com.example.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommonModel {
    private int pageRows;
    private int pageNo;
    private int offset;
    private int totalCount;
}
