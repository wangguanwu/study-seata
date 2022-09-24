package com.gw.seata.tcc.common.entity;

import lombok.Data;

/**
 * @author guanwu
 * @created on 2022-09-22 11:22:14
 **/

@Data
public class Order {
    private Long id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;
    private Integer status;
}
