package com.haiwen.platform.portal.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.utils.Query;
import com.haiwen.platform.common.utils.ValidatorUtils;
import com.haiwen.platform.service.entity.Role;
import com.haiwen.platform.service.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 系统角色表 控制器
 *
 * @author hc
 * @date 2019-04-27
 */
@Slf4j
@RestController
@Api(tags = "系统角色表接口")
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultData<IPage<Role>> list(@RequestBody Query query) {
        IPage<Role> page = query.getPagination();
        QueryWrapper<Role> queryWrapper = query.getQueryWrapper();
        return ResultData.ok(roleService.page(page, queryWrapper));
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResultData<Role> info(@PathVariable("id") Integer id) {
        Role entity = roleService.getById(id);
        return ResultData.ok(entity);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData<String> save(@RequestBody Role role) {
        roleService.save(role);
        return ResultData.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData<String> update(@RequestBody Role role) {
        ValidatorUtils.validateEntity(role);
        roleService.updateById(role);
        return ResultData.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultData<String> delete(@RequestBody Integer[] ids) {
        roleService.removeByIds(Arrays.asList(ids));
        return ResultData.ok();
    }

}
