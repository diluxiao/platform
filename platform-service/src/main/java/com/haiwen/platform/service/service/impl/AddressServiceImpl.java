package com.haiwen.platform.service.service.impl;

import com.haiwen.platform.service.entity.Address;
import com.haiwen.platform.service.mapper.AddressMapper;
import com.haiwen.platform.service.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 行政区域地址表 服务实现类
 * </p>
 *
 * @author hc
 * @since 2019-04-27
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
