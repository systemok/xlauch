package com.xlauch.web.mapper.deve;

import com.baomidou.mybatisplus.plugins.Page;
import com.xlauch.core.supers.mapper.SuperMapper;
import com.xlauch.web.entity.deve.DeveFileInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  类描述: 文件信息表 Mapper 接口
 * </p>
 * @author 伊凡
 * @since 2017-12-05
 * @version 0.1
 */
public interface DeveFileInfoMapper extends SuperMapper<DeveFileInfo> {

    /**
     * 列表分页查询
     * @param page
     * @param paramMap
     * @return
     */
    List<DeveFileInfo> selectDeveFileInfoList(Page<DeveFileInfo> page, Map paramMap);
}