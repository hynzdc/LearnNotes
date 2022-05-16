package com.hyn.jwt.service.impl;

import com.hyn.jwt.entity.Books;
import com.hyn.jwt.mapper.BooksMapper;
import com.hyn.jwt.service.BooksService;
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
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {

}
