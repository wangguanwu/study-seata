package com.gw.seata.tcc.account.controller;

import com.gw.seata.tcc.account.controller.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guanwu
 * @created on 2022-09-22 16:33:58
 **/

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/debit")
    public boolean debit(String userId, int money) throws Exception {
        accountService.debit(userId, money);
        return true;
    }
}
