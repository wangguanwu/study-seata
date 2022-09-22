package com.gw.seata.account.service.impl;

import com.gw.seata.account.service.AccountService;
import com.gw.seata.common.entity.Account;
import com.gw.seata.common.mapper.AccountMapper;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author guanwu
 * @created on 2022-09-22 16:35:22
 **/

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Transactional
    @Override
    public void debit(String userId, int money) {
        log.info("===============用户账户扣款=============");
        log.info("当前XID:{}", RootContext.getXID());
        checkBalance(userId, money);

        log.info("==========开始扣减用户 {} 余额==========", userId);

        int record = accountMapper.reduceBalance(userId, money);
        log.info("扣减用户{} 余额结果:{}", userId, record > 0 ? "成功" : "失败");
    }

    private void checkBalance(String userId, int money) {
        log.info("检查用户{}余额", userId);
        Account account = accountMapper.selectbyUserId(userId);
        if (account.getMoney() < money) {
            log.warn("用户 {}余额不足, 当前余额: {}, 扣减金额:{}", userId, account.getMoney(), money);
            throw new RuntimeException("余额不足");
        }
    }
}
