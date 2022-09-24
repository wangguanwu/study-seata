package com.gw.seata.tcc.account.controller.service.impl;

import com.gw.seata.tcc.account.controller.service.AccountService;
import com.gw.seata.tcc.common.entity.Account;
import com.gw.seata.tcc.common.mapper.AccountMapper;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

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
    public boolean debit(String userId, int money) {
        log.info("===============用户账户扣款=============");
        log.info("当前XID:{}", RootContext.getXID());
        checkBalance(userId, money);

        log.info("==========开始冻结用户 {} 余额==========", userId);

        int record = accountMapper.freezeBalance(userId, money);

        log.info("冻结用户{}金额， 冻结结果:{}", userId, record > 0 ? "成功" : "失败");
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("=============commit 扣减冻结金额=================");
        String userId = Objects.requireNonNull(actionContext.getActionContext("userId")).toString();
        int money = Integer.parseInt(Objects.requireNonNull(actionContext.getActionContext("money")).toString());

        int status = accountMapper.reduceFreezeBalance(userId, money);

        log.info("扣减用户冻结金额{} 扣减结果:{}", userId, status > 0 ? "成功" : "失败");

        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("=============rollback 解冻金额=================");

        String userId = Objects.requireNonNull(actionContext.getActionContext("userId")).toString();
        int money = Integer.parseInt((Objects.requireNonNull(actionContext.getActionContext("money")).toString()));
        int status = accountMapper.unfreezeBalance(userId, money);

        log.info("解冻用户金额{} 解冻结果:{}", userId, status > 0 ? "成功" : "失败");
        return true;
    }

    private void checkBalance(String userId, int money) {
        log.info("检查用户{}余额", userId);
        Account account = accountMapper.selectByUserId(userId);
        if (account.getMoney() < money) {
            log.warn("用户 {}余额不足, 当前余额: {}, 扣减金额:{}", userId, account.getMoney(), money);
            throw new RuntimeException("余额不足");
        }
    }
}
