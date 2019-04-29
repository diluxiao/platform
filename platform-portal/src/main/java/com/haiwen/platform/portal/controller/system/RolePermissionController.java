package com.haiwen.platform.portal.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.utils.Query;
import com.haiwen.platform.common.utils.ValidatorUtils;
import com.haiwen.platform.portal.controller.base.BaseController;
import com.haiwen.platform.service.entity.RolePermission;
import com.haiwen.platform.service.service.RolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 角色权限关系表 控制器
 *
 * @author hc
 * @date 2019-04-27
 */
@Slf4j
@RestController
@Api(tags = "角色权限关系表接口")
@RequestMapping("/api/rolePermission")
public class RolePermissionController extends BaseController {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultData<IPage<RolePermission>> list(@RequestBody Query query) {
        IPage<RolePermission> page = query.getPagination();
        QueryWrapper<RolePermission> queryWrapper = query.getQueryWrapper();
        return ResultData.ok(rolePermissionService.page(page, queryWrapper));
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResultData<RolePermission> info(@PathVariable("id") Integer id) {
        RolePermission entity = rolePermissionService.getById(id);
        return ResultData.ok(entity);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData<String> save(@RequestBody RolePermission rolePermission) {
        rolePermissionService.save(rolePermission);
        return ResultData.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData<String> update(@RequestBody RolePermission rolePermission) {
        ValidatorUtils.validateEntity(rolePermission);
        rolePermissionService.updateById(rolePermission);
        return ResultData.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultData<String> delete(@RequestBody Integer[] ids) {
        rolePermissionService.removeByIds(Arrays.asList(ids));
        return ResultData.ok();
    }

}
