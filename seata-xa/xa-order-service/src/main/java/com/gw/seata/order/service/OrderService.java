package com.gw.seata.order.service;

import com.gw.seata.common.entity.Order;
import com.gw.seata.order.vo.OrderVo;
import io.seata.core.exception.TransactionException;

/**
 * @author guanwu
 * @created 2022/9/21 22:30
 */
public interface OrderService {

    Order saveOrder(OrderVo orderVo)throws TransactionException;
}
