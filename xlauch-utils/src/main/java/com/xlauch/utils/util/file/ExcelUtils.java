package com.xlauch.utils.util.file;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 类描述 : Excel 操作工具类
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/14
 */
public class ExcelUtils {


    public static void testReader() {
        ExcelReader reader;
        //通过sheet编号获取
        reader = ExcelUtil.getReader("E:\\test\\1.xlsx", 0);
        List<Map<String, Object>> rows = reader.readAll();
        rows.forEach(row -> {
            row.keySet().forEach(key -> System.out.println(key + " \t=  " + row.get(key)));
        });
    }

    public static void testWrite1() {
        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("E:\\test\\1.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容
        writer.write(rows);
        //关闭writer，释放内存
        writer.close();
    }

    public static void testWrite2() {
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());

        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("E:\\test\\12.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容
        writer.write(rows);
        //关闭writer，释放内存
        writer.close();
    }

    public static void testWrite3() {
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());

        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("E:\\test\\12.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();
        //自定义标题别名
        Map<String, String> alias = MapUtil.newHashMap();
        alias.put("姓名", "name");
        alias.put("age", "年龄");
        writer.setHeaderAlias(alias);

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容
        writer.write(rows);
        //关闭writer，释放内存
        writer.close();
    }


    public static void main(String[] args) {

        testWrite3();
    }


}
