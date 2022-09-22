package com.gw.seata.storage.service;

/**
 * @author guanwu
 * @created on 2022-09-22 19:22:04
 **/
public interface StorageService {

    /**
     * 扣减库存
     * @param commodityCode 商品编号
     * @param count 扣除数量
     */
    void deduct(String commodityCode, int count);
}
