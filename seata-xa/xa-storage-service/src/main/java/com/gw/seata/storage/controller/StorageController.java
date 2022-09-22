package com.gw.seata.storage.controller;

import com.gw.seata.storage.service.StorageService;
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
    public Boolean deduct(String commodityCode, Integer code) {
        storageService.deduct(commodityCode, code);
        return true;
    }
}
