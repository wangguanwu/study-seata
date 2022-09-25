package com.gw.seata.tcc.order.service.impl;

import com.gw.seata.tcc.common.entity.Order;
import com.gw.seata.tcc.common.entity.OrderStatus;
import com.gw.seata.tcc.common.mapper.OrderMapper;
import com.gw.seata.tcc.order.service.OrderService;
import com.gw.seata.tcc.order.vo.OrderVo;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author guanwu
 * @created on 2022-09-22 14:21:33
 **/

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order prepareSaveOrder(OrderVo orderVo, @BusinessActionContextParameter(paramName = "orderId")Long orderId)  {
        Order order = new Order();
        order.setId(orderId);
        order.setUserId(orderVo.getUserId());
        order.setCommodityCode(orderVo.getCommodityCode());
        order.setCount(orderVo.getCount());
        order.setMoney(orderVo.getMoney());
        order.setStatus(OrderStatus.INIT.getValue());
        Integer saveOrderRecord = orderMapper.insert(order);
        log.info("保存订单{}", saveOrderRecord > 0 ? "成功" : "失败");
        return order;
    }


    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("=============commit=============");
        long orderId = Long.parseLong(Objects.
                requireNonNull(actionContext.getActionContext("orderId")).toString());
        Integer status = orderMapper.updateOrderStatus(orderId, OrderStatus.SUCCESS.getValue());
        log.info("更新订单Id: {} {}", orderId, status > 0 ? "成功" : "失败");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("==========rollback============");
        long orderId = Long.parseLong(Objects.requireNonNull(actionContext.getActionContext("orderId")).toString());
        //更新订单状态为支付失败
        Integer updateOrderRecord = orderMapper.updateOrderStatus(orderId,OrderStatus.FAIL.getValue());
        log.info("更新订单id:{} {}", orderId, updateOrderRecord > 0 ? "成功" : "失败");

        return true;
    }
}
