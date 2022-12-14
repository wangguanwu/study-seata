package com.gw.seata.tcc.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author guanwu
 * @created on 2022-09-21 16:59:36
 **/

@SpringBootApplication(scanBasePackages = {"com.gw.seata"})
@EnableFeignClients
public class TCCOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCOrderServiceApplication.class, args);
    }
}
