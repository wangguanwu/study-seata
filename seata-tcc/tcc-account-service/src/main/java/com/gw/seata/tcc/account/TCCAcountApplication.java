package com.gw.seata.tcc.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guanwu
 * @created on 2022-09-22 16:26:26
 **/

@SpringBootApplication(scanBasePackages = {"com.gw.seata"})
public class TCCAcountApplication {

    public static void main(String[] args) {
        SpringApplication.run(TCCAcountApplication.class, args);
    }
}
