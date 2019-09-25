package com.xlauch.web.service.sys.impl;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xlauch.core.config.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.context.ActionContext;
import com.xlauch.utils.page.DataGrid;

import com.xlauch.web.entity.sys.SysDict;
import com.xlauch.web.mapper.sys.SysDictMapper;
import com.xlauch.web.service.sys.ISysDictService;
import com.xlauch.core.supers.service.SuperServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  类描述: 系统字典表 服务实现类
 * </p>
 * @author huangxy
 * @since 2017-11-21
 * @version 0.1
 */
@Service
public class SysDictServiceImpl extends SuperServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Autowired
    private SysDictMapper sysDictMapper ;

    @Autowired
    private RedisManager redisManager;

    @Value("sys.dict.key")
    private String dictKey;

    @Override
    public DataGrid selectSysDictList() {
        ActionContext<SysDict> actionContext = ActionContext.getContext() ;
        Page<SysDict> page = actionContext.getPage() ;
        page.setRecords(sysDictMapper.selectSysDictList(page , actionContext.getDataMap()));
        return pageToDataGrid(page);
    }

    @Override
    @Transactional
    public boolean insert(SysDict sysDict){
        boolean flag = super.insert(sysDict);
        //加入缓存
        redisManager.hdel(dictKey, sysDict.getCode());
        selectSysDictByCodeCache(sysDict.getCode());
        return flag;
    }

    @Override
    @Transactional
    public boolean updateById(SysDict sysDict) {
        boolean flag = super.updateById(sysDict);
        //加入缓存
        redisManager.hdel(dictKey, sysDict.getCode());
        selectSysDictByCodeCache(sysDict.getCode());
        return flag;
    }

    /**
     * <p>
     * 方法描述：获取字典带缓存
     * </p>
     *
     * @author huangxy
     * @since 2017/11/21 11:26
     * @version 0.1
     */
    @Override
    public List<SysDict> selectSysDictByCodeCache(SysDict sysDict){
        List<SysDict> list = new ArrayList();
        if(redisManager.hexists(dictKey, sysDict.getCode())){
            String tmp = redisManager.hget(dictKey, sysDict.getCode());
            list = JSONArray.parseArray(tmp, SysDict.class);
        } else {
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.setEntity(sysDict);
            list = sysDictMapper.selectList(wrapper);
            redisManager.hset(dictKey, sysDict.getCode(), JSONArray.toJSONString(list));
        }
        return list;

    }

    @Override
    public List<SysDict> selectSysDictByCodeCache(String code) {
        SysDict sysDict =  new SysDict();
        sysDict.setCode(code);
        sysDict.setStatus(1);
        return selectSysDictByCodeCache(sysDict);
    }

}
