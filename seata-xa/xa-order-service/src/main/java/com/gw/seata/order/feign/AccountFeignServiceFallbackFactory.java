package com.gw.seata.order.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author guanwu
 * @created on 2022-09-22 14:15:01
 **/
@Component
@Slf4j
public class AccountFeignServiceFallbackFactory implements FallbackFactory<AccountFeignService> {
    @Override
    public AccountFeignService create(Throwable throwable) {
        return ((userId, money) -> {
            log.error("账户异常降级了....userId:{},ex:{} ", userId, throwable.getMessage());
            return false;
        });
    }
}
