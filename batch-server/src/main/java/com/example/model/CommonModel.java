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

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return (getPageNo() - 1) * getPageRows();
    }
}
