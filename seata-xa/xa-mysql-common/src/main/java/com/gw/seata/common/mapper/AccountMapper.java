package com.gw.seata.common.mapper;

import com.gw.seata.common.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author guanwu
 * @created on 2022-09-22 11:26:07
 **/

@Repository
public interface AccountMapper {

    @Select("select id, user_id, money from account_tbl where user_id = #{userId}")
    Account selectbyUserId(@Param("userId") String userId);

    @Update("update account_tbl set money = money - #{money} where user_id = #{userId}")
    Integer reduceBalance(@Param("userId") String userId, @Param("money") Integer money);

}
