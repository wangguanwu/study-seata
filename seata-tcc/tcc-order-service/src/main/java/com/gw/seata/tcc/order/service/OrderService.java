package com.gw.seata.tcc.order.service;

import com.gw.seata.tcc.common.entity.Order;
import com.gw.seata.tcc.order.vo.OrderVo;
import io.seata.core.exception.TransactionException;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author guanwu
 * @created 2022/9/21 22:30
 */

@LocalTCC
public interface OrderService {

    /**
     * TCC的 try方法，保存订单信息，订单状态为支付中
     * commitMethod = commit 为二阶段确认
     * rollbackMethod = rollback 为二阶段cancel
     * BussinessActionContextParameter注解，传递参数到二阶段中
     * userTCCFence seata1.5.1的新特性，解决TCC 幂等、空回滚、悬挂等问题，添加日志表tcc_fence_log
     * @param orderVo
     * @param orderId
     * @return
     * @throws TransactionException
     */
    @TwoPhaseBusinessAction(name = "prepareSaveOrder", commitMethod = "commit", rollbackMethod = "rollback",
    useTCCFence = true)
    Order saveOrder(OrderVo orderVo, @BusinessActionContextParameter(paramName = "orderId") Long orderId) ;

    boolean commit (BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
