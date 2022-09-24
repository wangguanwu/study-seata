package com.gw.seata.tcc.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guanwu
 * @created on 2022-09-22 19:17:03
 **/

@SpringBootApplication(scanBasePackages = {"com.gw.seata"})
public class TCCStorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCStorageServiceApplication.class, args);
    }
}
