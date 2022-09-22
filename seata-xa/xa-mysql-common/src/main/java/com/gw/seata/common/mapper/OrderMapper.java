package com.gw.seata.common.mapper;

import com.gw.seata.common.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author guanwu
 * @created on 2022-09-22 11:28:52
 **/

@Repository
public interface OrderMapper {

    /**
     * 插入订单记录
     * @param record
     * @return
     */
    @Insert("insert into order_tbl (user_id, commodity_code, count ,status, money)" +
            "values (#{userId}, #{commodityCode}, #{count}, #{status}, #{money})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insert(Order record);


    /**
     * 更新订单状态
     * @param id
     * @param status
     * @return
     */
    @Update("update order_tbl set status #{status} where id = #{id}")
    Integer updateOrderStatus(@Param("id") Integer id, @Param("status") int status);
}
