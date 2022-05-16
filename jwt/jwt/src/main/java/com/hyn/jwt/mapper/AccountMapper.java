package com.hyn.jwt.mapper;

import com.hyn.jwt.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyn
 * @since 2021-10-21
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
