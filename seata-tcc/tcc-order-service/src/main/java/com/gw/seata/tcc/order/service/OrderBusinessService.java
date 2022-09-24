package com.gw.seata.tcc.order.service;

import com.gw.seata.tcc.common.entity.Order;
import com.gw.seata.tcc.order.vo.OrderVo;
import io.seata.core.exception.TransactionException;

/**
 * @author guanwu
 * @created 2022/9/24 14:07
 */
public interface OrderBusinessService {
    Order saveOrder(OrderVo orderVo) throws TransactionException;
}
