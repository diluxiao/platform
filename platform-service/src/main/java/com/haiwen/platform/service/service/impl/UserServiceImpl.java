package com.haiwen.platform.service.service.impl;

import com.haiwen.platform.service.entity.User;
import com.haiwen.platform.service.mapper.UserMapper;
import com.haiwen.platform.service.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author hc
 * @since 2019-04-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
