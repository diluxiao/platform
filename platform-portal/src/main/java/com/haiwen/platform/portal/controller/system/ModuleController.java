package com.haiwen.platform.portal.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.utils.Query;
import com.haiwen.platform.common.utils.ValidatorUtils;
import com.haiwen.platform.portal.controller.base.BaseController;
import com.haiwen.platform.service.entity.Module;
import com.haiwen.platform.service.service.ModuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 系统模块表 控制器
 *
 * @author hc
 * @date 2019-04-27
 */
@Slf4j
@RestController
@Api(tags = "系统模块表接口")
@RequestMapping("/api/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultData<IPage<Module>> list(@RequestBody Query query) {
        IPage<Module> page = query.getPagination();
        QueryWrapper<Module> queryWrapper = query.getQueryWrapper();
        return ResultData.ok(moduleService.page(page, queryWrapper));
    }


    /**
     * 信息
     */
    @ApiOperation("信息")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResultData<Module> info(@PathVariable("id") Integer id) {
        Module entity = moduleService.getById(id);
        return ResultData.ok(entity);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultData<String> save(@RequestBody Module module) {
        moduleService.save(module);
        return ResultData.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData<String> update(@RequestBody Module module) {
        ValidatorUtils.validateEntity(module);
        moduleService.updateById(module);
        return ResultData.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultData<String> delete(@RequestBody Integer[] ids) {
        moduleService.removeByIds(Arrays.asList(ids));
        return ResultData.ok();
    }

}
