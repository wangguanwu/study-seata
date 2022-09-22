package com.gw.seata.account.service;

/**
 * @author guanwu
 * @created on 2022-09-22 16:34:21
 **/


public interface AccountService {

    /**
     * 扣款
     * @param userId
     * @param money
     */
    void debit(String userId, int money) ;
}
