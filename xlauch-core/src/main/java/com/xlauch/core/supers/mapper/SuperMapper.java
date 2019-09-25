package com.xlauch.core.supers.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 类描述    :  mapper 父类，注意这个类不要让 mp 扫描到！！  <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : SuperMapper <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/10/27 17:18  <br/>
 * @version 0.1
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    // 这里可以放一些公共的方法
    @SelectProvider(type=SuperSqlProvider.class, method="selectByConditionWithSql")
    List<Map> selectByConditionWithSql(@Param("sql") String sql, @Param("paramMap") Map paramMap);

}
