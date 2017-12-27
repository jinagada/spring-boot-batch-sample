package com.example.job.code;

import lombok.Getter;

public enum CommonCodeEnum {
    DUMMY("DUMMY", "Dummy ExitStatus");

    @Getter
    private String code;
    @Getter
    private String desc;

    CommonCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
