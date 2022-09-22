package com.gw.seata.common.entity;

/**
 * @author guanwu
 * @created on 2022-09-22 11:23:04
 **/
public enum OrderStatus {
    INIT(0),

    SUCCESS(1),

    FAIL(2);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
