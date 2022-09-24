package com.gw.seata.tcc.common.entity;

import lombok.Data;

/**
 * @author guanwu
 * @created on 2022-09-22 11:21:08
 **/

@Data
public class Account {
    private Integer id;
    private String userId;
    private Integer money;
}
