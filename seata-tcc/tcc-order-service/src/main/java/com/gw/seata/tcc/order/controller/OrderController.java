package com.gw.seata.tcc.order.controller;

import com.gw.seata.tcc.common.entity.Order;
import com.gw.seata.tcc.common.vo.ResultVo;
import com.gw.seata.tcc.order.service.OrderBusinessService;
import com.gw.seata.tcc.order.vo.OrderVo;
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

    private final OrderBusinessService orderBusinessService;

    public OrderController(OrderBusinessService orderBusinessService) {
        this.orderBusinessService = orderBusinessService;
    }


    @PostMapping("/createOrder")
    public ResultVo createOrder(@RequestBody OrderVo orderVo) throws Exception {
        log.info("收到下单请求,用户：{}, 商品编号:{}", orderVo.getUserId(), orderVo.getCommodityCode());
        Order order = orderBusinessService.saveOrder(orderVo);
        return ResultVo.ok().put("order", order);
    }


}
