package com.gw.seata.tcc.storage.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author guanwu
 * @created on 2022-09-22 19:22:04
 **/

@LocalTCC
public interface StorageService {

    /**
     * 扣减库存
     * @param commodityCode 商品编号
     * @param count 扣除数量
     */

    @TwoPhaseBusinessAction(name = "deductStorage", useTCCFence = true)
    void deduct(@BusinessActionContextParameter("commodityCode") String commodityCode,
                @BusinessActionContextParameter("count") int count);

    boolean commit(BusinessActionContext businessActionContext);

    boolean rollback(BusinessActionContext businessActionContext);
}
