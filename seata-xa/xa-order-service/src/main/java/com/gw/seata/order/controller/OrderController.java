package com.gw.seata.order.controller;

import com.gw.seata.common.entity.Order;
import com.gw.seata.common.vo.ResultVo;
import com.gw.seata.order.service.OrderService;
import com.gw.seata.order.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author guanwu
 * @created on 2022-09-22 16:14:28
 **/

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResultVo createOrder(@RequestBody OrderVo orderVo) throws Exception {
        log.info("收到下单请求,用户：{}, 商品编号:{}", orderVo.getUserId(), orderVo.getCommodityCode());
        Order order = orderService.saveOrder(orderVo);
        return ResultVo.ok().put("order", order);
    }


}
