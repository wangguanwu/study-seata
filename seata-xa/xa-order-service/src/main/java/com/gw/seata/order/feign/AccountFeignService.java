package com.gw.seata.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author guanwu
 * @created on 2022-09-22 14:10:00
 **/

@FeignClient(name = "xa-account-service", path = "/account")
@Repository
public interface AccountFeignService {

    @RequestMapping("/debit")
    boolean debit(@RequestParam("userId") String userId, @RequestParam("money")
                  int money);
}
