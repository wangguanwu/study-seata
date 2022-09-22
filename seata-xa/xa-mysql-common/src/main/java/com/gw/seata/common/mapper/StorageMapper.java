package com.gw.seata.common.mapper;

import com.gw.seata.common.entity.Storage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author guanwu
 * @created on 2022-09-22 11:33:23
 **/

@Repository
public interface StorageMapper {

    /**
     * 获取库存
     * @param commodityCode
     * @return
     */
    @Select("select id, commodity_code, count from storage_tbl where commodity_code = #{commodityCode}")
    Storage findByCommodityCode(@Param("commodityCode") String commodityCode);


    /**
     * 扣减库存
     * @param commodityCode
     * @param count
     * @return
     */
    @Update("update storage_tbl set count = count - #{count} where commodity_code = #{commodityCode}")
    int reduceStorage(@Param("commodityCode") String commodityCode,
                      @Param("count") Integer count);

}
