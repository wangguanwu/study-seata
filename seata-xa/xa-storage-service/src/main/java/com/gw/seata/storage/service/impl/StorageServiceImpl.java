package com.gw.seata.storage.service.impl;

import com.gw.seata.common.entity.Storage;
import com.gw.seata.common.mapper.StorageMapper;
import com.gw.seata.storage.service.StorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        log.info("开始扣减 {} 库存", commodityCode);

        int status = storageMapper.reduceStorage(commodityCode, count);

        log.info("扣减 {} 库存结果: {}", commodityCode, status > 0 ? "成功" : "失败");
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
