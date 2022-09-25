package com.gw.seata.tcc.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author guanwu
 * @created on 2022-09-22 14:12:43
 **/

@FeignClient(name = "tcc-storage-service", path = "/storage")
@Repository
public interface StorageFeignService {

    @RequestMapping(path = "/deduct")
    Boolean  deduct(@RequestParam("commodityCode") String commodityCode,
                   @RequestParam("count") Integer count);
}
