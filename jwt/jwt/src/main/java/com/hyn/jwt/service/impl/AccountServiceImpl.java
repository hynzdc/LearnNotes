package com.hyn.jwt.service.impl;

import com.hyn.jwt.entity.Account;
import com.hyn.jwt.mapper.AccountMapper;
import com.hyn.jwt.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyn
 * @since 2021-10-21
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
