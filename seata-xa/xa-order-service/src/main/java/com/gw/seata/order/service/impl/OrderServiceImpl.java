package com.gw.seata.order.service.impl;

import com.gw.seata.common.entity.Order;
import com.gw.seata.common.entity.OrderStatus;
import com.gw.seata.common.mapper.OrderMapper;
import com.gw.seata.order.feign.AccountFeignService;
import com.gw.seata.order.feign.StorageFeignService;
import com.gw.seata.order.service.OrderService;
import com.gw.seata.order.vo.OrderVo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author guanwu
 * @created on 2022-09-22 14:21:33
 **/

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountFeignService accountFeignService;

    @Autowired
    private StorageFeignService storageFeignService;


    @Transactional
    @GlobalTransactional(name = "createOrder", rollbackFor = Exception.class)
    @Override
    public Order saveOrder(OrderVo orderVo)  {
        log.info("====================用户下单===============");
        log.info("当前 XID: {}", RootContext.getXID());

        Order order = new Order();
        BeanUtils.copyProperties(orderVo, order);
        order.setStatus(OrderStatus.INIT.getValue());
        Integer saveOrderRecord = orderMapper.insert(order);
        log.info("保存订单{}", saveOrderRecord > 0 ? "成功" : "失败");

        //扣减库存
        storageFeignService.deduct(order.getCommodityCode(), order.getCount());

        //扣减余额
        accountFeignService.debit(order.getUserId(), order.getMoney());

        //更新订单状态
        Integer updateOrder = orderMapper.updateOrderStatus(order.getId(), OrderStatus.SUCCESS.getValue());
        log.info("更新订单 id:{} {}", order.getId(), updateOrder > 0 ? "成功" : "失败");
        return order;
    }
}
