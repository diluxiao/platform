package com.haiwen.platform.portal.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.utils.Query;
import com.haiwen.platform.common.utils.ValidatorUtils;
import com.haiwen.platform.portal.controller.base.BaseController;
import com.haiwen.platform.service.entity.Permission;
import com.haiwen.platform.service.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 系统权限表 控制器
 *
 * @author hc
 * @date 2019-04-27
 */
@Slf4j
@RestController
@Api(tags = "系统权限表接口")
@RequestMapping("/api/permission")
public class PermissionController extends BaseController{

    @Autowired
    private PermissionService permissionService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultData<IPage<Permission>> list(@RequestBody Query query) {
        IPage<Permission> page = query.getPagination();
        QueryWrapper<Permission> queryWrapper = query.getQueryWrapper();
        return ResultData.ok(permissionService.page(page, queryWrapper));
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResultData<Permission> info(@PathVariable("id") Integer id) {
        Permission entity = permissionService.getById(id);
        return ResultData.ok(entity);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData<String> save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return ResultData.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData<String> update(@RequestBody Permission permission) {
        ValidatorUtils.validateEntity(permission);
        permissionService.updateById(permission);
        return ResultData.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultData<String> delete(@RequestBody Integer[] ids) {
        permissionService.removeByIds(Arrays.asList(ids));
        return ResultData.ok();
    }

}
