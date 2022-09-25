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
    /**
     * TCC的try方法：保存订单信息，状态为支付中
     *
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     *  name = 该tcc的bean名称,全局唯一
     *  commitMethod = commit 为二阶段确认方法
     *  rollbackMethod = rollback 为二阶段取消方法
     *  BusinessActionContextParameter注解 传递参数到二阶段中
     *  useTCCFence seata1.5.1的新特性，用于解决TCC幂等，悬挂，空回滚问题，需增加日志表tcc_fence_log
     */
    @TwoPhaseBusinessAction(name = "prepareSaveOrder", commitMethod = "commit", rollbackMethod = "rollback", useTCCFence = true)
    Order prepareSaveOrder(OrderVo orderVo, @BusinessActionContextParameter(paramName = "orderId") Long orderId);

    /**
     *
     * TCC的confirm方法：订单状态改为支付成功
     *
     * 二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * TCC的cancel方法：订单状态改为支付失败
     * 二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}
