package com.gw.seata.order.vo;

import lombok.Data;

/**
 * @author guanwu
 * @created 2022/9/21 22:31
 */

@Data
public class OrderVo {
    private String userId;
    /**ååįžå·**/
    private String commodityCode;

    private Integer count;

    private Integer money;
}
