package com.haiwen.platform.portal.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.utils.Query;
import com.haiwen.platform.common.utils.ValidatorUtils;
import com.haiwen.platform.service.entity.Address;
import com.haiwen.platform.service.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 行政区域地址表 控制器
 *
 * @author hc
 * @date 2019-04-27
 */
@Slf4j
@RestController
@Api(tags = "行政区域地址表接口")
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultData<IPage<Address>> list(@RequestBody Query query) {
        IPage<Address> page = query.getPagination();
        QueryWrapper<Address> queryWrapper = query.getQueryWrapper();
        return ResultData.ok(addressService.page(page, queryWrapper));
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResultData<Address> info(@PathVariable("id") Integer id) {
        Address entity = addressService.getById(id);
        return ResultData.ok(entity);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData<String> save(@RequestBody Address address) {
        addressService.save(address);
        return ResultData.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData<String> update(@RequestBody Address address) {
        ValidatorUtils.validateEntity(address);
        addressService.updateById(address);
        return ResultData.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultData<String> delete(@RequestBody Integer[] ids) {
        addressService.removeByIds(Arrays.asList(ids));
        return ResultData.ok();
    }

}
