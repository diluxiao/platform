package com.haiwen.platform.portal.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.utils.Query;
import com.haiwen.platform.common.utils.ValidatorUtils;
import com.haiwen.platform.portal.controller.base.BaseController;
import com.haiwen.platform.service.entity.UserRole;
import com.haiwen.platform.service.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 系统用户角色关系表 控制器
 *
 * @author hc
 * @date 2019-04-27
 */
@Slf4j
@RestController
@Api(tags = "系统用户角色关系表接口")
@RequestMapping("/api/userRole")
public class UserRoleController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultData<IPage<UserRole>> list(@RequestBody Query query) {
        IPage<UserRole> page = query.getPagination();
        QueryWrapper<UserRole> queryWrapper = query.getQueryWrapper();
        return ResultData.ok(userRoleService.page(page, queryWrapper));
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResultData<UserRole> info(@PathVariable("id") Integer id) {
        UserRole entity = userRoleService.getById(id);
        return ResultData.ok(entity);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData<String> save(@RequestBody UserRole userRole) {
        userRoleService.save(userRole);
        return ResultData.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData<String> update(@RequestBody UserRole userRole) {
        ValidatorUtils.validateEntity(userRole);
        userRoleService.updateById(userRole);
        return ResultData.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultData<String> delete(@RequestBody Integer[] ids) {
        userRoleService.removeByIds(Arrays.asList(ids));
        return ResultData.ok();
    }

}
