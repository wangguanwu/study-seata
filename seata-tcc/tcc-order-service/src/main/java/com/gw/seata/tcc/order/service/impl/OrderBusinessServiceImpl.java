package com.gw.seata.tcc.order.service.impl;


import com.gw.seata.tcc.common.entity.Order;
import com.gw.seata.tcc.order.feign.AccountFeignService;
import com.gw.seata.tcc.order.feign.StorageFeignService;
import com.gw.seata.tcc.order.service.OrderBusinessService;
import com.gw.seata.tcc.order.service.OrderService;
import com.gw.seata.tcc.order.utils.UUIDGenerator;
import com.gw.seata.tcc.order.vo.OrderVo;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author guanwu
 * @created 2022/9/24 14:08
 */

@Service
@Slf4j
public class OrderBusinessServiceImpl implements OrderBusinessService {

    private final AccountFeignService accountFeignService;

    private final StorageFeignService storageFeignService;

    private final OrderService orderService;

    public OrderBusinessServiceImpl(AccountFeignService accountFeignService, StorageFeignService storageFeignService, OrderService orderService) {
        this.accountFeignService = accountFeignService;
        this.storageFeignService = storageFeignService;
        this.orderService = orderService;
    }

    @GlobalTransactional(name = "createMyOrder", rollbackFor = Exception.class)
    @Override
    public Order saveOrder(OrderVo orderVo)   {
        log.info("=============用户下单=================");
        log.info("当前 XID: {}", RootContext.getXID());

        //获取全局唯一订单号  测试使用
        Long orderId = UUIDGenerator.generateUUID();

        //阶段一： 创建订单
        Order order = orderService.saveOrder(orderVo,orderId);

        //扣减库存
        storageFeignService.deduct(orderVo.getCommodityCode(), orderVo.getCount());
        //扣减余额
        accountFeignService.debit(orderVo.getUserId(), orderVo.getMoney());

        return order;
    }
}
