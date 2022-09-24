package com.gw.seata.tcc.storage.controller;

import com.gw.seata.tcc.storage.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanwu
 * @created on 2022-09-22 19:44:44
 **/

@RestController
@RequestMapping("/storage")
public class StorageController {

    private final StorageService storageService;


    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping("/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return true;
    }
}
