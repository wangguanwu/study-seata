package com.gw.seata.tcc.account.controller.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author guanwu
 * @created on 2022-09-22 16:34:21
 **/

@LocalTCC
public interface AccountService {



    @TwoPhaseBusinessAction(name = "debit", useTCCFence = true)
    boolean debit(@BusinessActionContextParameter(paramName = "userId") String userId,
                  @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * 提交事务，二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * 回滚事务，二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);

}
