package com.gw.seata.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guanwu
 * @created on 2022-09-22 19:17:03
 **/

@SpringBootApplication(scanBasePackages = {"com.gw.seata"})
public class XAStorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XAStorageServiceApplication.class, args);
    }
}
