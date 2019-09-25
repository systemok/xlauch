package com.xlauch.utils.util.file;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类描述 : 文件写入工具
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/14
 */
public class FileWriterUtils {


    /**
     * 生成excel
     * @param filePath      生成的文件路径（必填）
     * @param title         标题（非必填）
     * @param rows          导出的数据（必填）
     * @param alias         标题别名（非必填）
     * @param beginRow      开始导出的行
     * @param beginCol      开始导出的列
     */
    public static void writeExcel(String filePath ,String title , List<Map<String, Object>> rows , Map<String, String> alias , int beginRow , int beginCol) {
        ExcelWriter writer = ExcelUtil.getWriter(filePath);

        //跳行
        writer.passRows(beginRow);

        //自定义标题别名
        if (alias !=  null) {
            writer.setHeaderAlias(alias);
        }

        //合并单元格后的标题行，使用默认标题样式
        if(StringUtils.isNotEmpty(title) && rows !=  null && rows.size() > 0) {
            writer.merge(rows.get(0).size() - 1, title);
        }

        //一次性写出内容
        writer.write(rows);

        //关闭writer，释放内存
        writer.close();
    }



}
