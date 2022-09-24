package com.gw.seata.tcc.storage.service.impl;

import com.gw.seata.tcc.common.entity.Storage;
import com.gw.seata.tcc.common.mapper.StorageMapper;
import com.gw.seata.tcc.storage.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author guanwu
 * @created on 2022-09-22 19:22:27
 **/

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final StorageMapper storageMapper;

    public StorageServiceImpl(StorageMapper storageMapper) {
        this.storageMapper = storageMapper;
    }

    @Transactional
    @Override
    public void deduct(String commodityCode, int count) {

        log.info("==============扣减库存===============");
        log.info("当前XID: {}", RootContext.getXID());

        checkStock(commodityCode, count);

        log.info("开始冻结 {} 库存", commodityCode);

        int status = storageMapper.freezeStorage(commodityCode, count);

        log.info("冻结 {} 库存结果: {}", commodityCode, status > 0 ? "成功" : "失败");
    }

    @Override
    public boolean commit(BusinessActionContext businessActionContext) {
        log.info("=============扣减冻结库存=================");
        String commodityCode = Objects.requireNonNull(businessActionContext.getActionContext("commodityCode")).toString();
        int count = Integer.parseInt(Objects.requireNonNull(businessActionContext.getActionContext("count")).toString());
        int status = storageMapper.reduceFreezeStorage(commodityCode, count);
        log.info("扣减 {} 冻结库存,结果:{}", commodityCode, status > 0 ? "成功" : "失败");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext businessActionContext) {
        log.info("=============解冻库存=================");
        String commodityCode = Objects.requireNonNull(businessActionContext.getActionContext("commodityCode")).toString();
        int count = Integer.parseInt(Objects.requireNonNull(businessActionContext.getActionContext("count")).toString());
        int status = storageMapper.unfreezeStorage(commodityCode, count);
        log.info("解冻 {} 库存,结果:{}", commodityCode, status > 0 ? "成功" : "失败");
        return true;
    }

    private void checkStock(String commodityCode, int count) {
        log.info("检查 {} 库存", commodityCode);

        Storage storage = storageMapper.findByCommodityCode(commodityCode);

        if(storage.getCount() < count) {
            log.warn("{} 库存不足, 当前库存: {}", commodityCode, count);
            throw new RuntimeException("库存不足");
        }
    }
}
