package com.xlauch.web.common.utils;


import com.xlauch.web.entity.deve.DeveObjectColumn;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2018/1/25
 */
public class DefaultFormGener implements FormGener {

    /**
     * 可用
     */
    public static final Integer ENABLE = 1;
    /**
     * 不可用
     */
    public static final Integer DISABLE = 0;
    /**
     * 宽度
     */
    public static final String WIDTH = "width: 200px;";
    /**
     * 文本域
     */
    public static final String MAX_WIDTH = "width: 610px;";
    /**
     * easyui 类型的表单
     */
    public static final String EASY_TYPE_FORM_STR = "<input name=\"#name#\" class=\"#formType#\" style=\"#width# #height#\" #multiline# data-options=\"#dataOptions#\" /> ";

    /**
     * 预设验证 validType:'email'
     */
    public static final Map<String , String> optMap = new HashMap<>();

    static {
        optMap.put("手机号","'mobile'");
        optMap.put("手机","'mobile'");
        optMap.put("移动电话","'mobile'");
        optMap.put("邮箱","'email'");
        optMap.put("电子邮箱","'email'");
        optMap.put("eMail","'email'");
        optMap.put("电话","'TEL'");
        optMap.put("邮编","'ZIP'");
        optMap.put("身份证","'idcard'");
    }




    /**
     * 生成表单
     *
     * @param deveObjectColumn
     */
    @Override
    public void generForm(DeveObjectColumn deveObjectColumn) {
        String name = deveObjectColumn.getCname();
        String formType = "easyui-textbox";
        String multiline = "";
        String width = WIDTH;
        String height = "" ;

        if (StringUtils.isEmpty(name)) {
            deveObjectColumn.setCname(deveObjectColumn.getEname());
        }
        deveObjectColumn.setUiCheck(generDataOption(deveObjectColumn.getWidth(),deveObjectColumn.getCname()));

        //设置宽度
        if(deveObjectColumn.getWidth() >= 200) {
            width = MAX_WIDTH;
            height = "height: 50px;";
            deveObjectColumn.setCols(3);
            deveObjectColumn.setWidth(610);
        }else if(deveObjectColumn.getWidth() >= 100) {
            width = MAX_WIDTH;
            deveObjectColumn.setCols(3);
            deveObjectColumn.setWidth(610);
        }else {
            deveObjectColumn.setWidth(200);
        }

        //根据字段备注，预估字段表单类型
        if (name.contains("日期") || name.contains("生日")) {
            formType = "easyui-datebox";
        } else if (name.contains("时间")) {
            formType = "easyui-datetimebox";
        } else if (name.contains("备注") || name.contains("描述")) {
            deveObjectColumn.setCols(3);
            width = MAX_WIDTH;
            multiline = "multiline=\"true\"";
        } else if (name.contains("密码") || name.contains("密钥")) {
            formType = "easyui-passwordbox";
        } else if (name.contains("数量") || name.contains("金额") || name.contains("宽度") || name.contains("高度") || name.contains("序号")  || name.contains("排序")  || name.contains("号码")  ) {
            formType = "easyui-numberbox";
        } else if (name.contains("是否")) {
            deveObjectColumn.setDictAble(ENABLE);
            deveObjectColumn.setDictStr("yesornot");
            deveObjectColumn.setDefaultValue("1");
            formType = "radio";
        } else if (name.contains("状态")) {
            deveObjectColumn.setDictAble(ENABLE);
            deveObjectColumn.setDictStr("status");
            deveObjectColumn.setDefaultValue("1");
            formType = "radio";
        } else if (name.contains("附件") || name.contains("文档")) {
            formType = "file";
        } else if (name.contains("头像") || name.contains("图片") || name.contains("照片") || name.contains("图标")) {
            formType = "image";
            width = "width: 16px;";
            height = "height: 16px;" ;
            deveObjectColumn.setWidth(16);
            deveObjectColumn.setHeight(16);
        } else if (deveObjectColumn.isPk()) {
            formType = "hidden";
        }

        deveObjectColumn.setFormType(formType);



        //拼接表单内容
        String formStr = "<input name=\"" + deveObjectColumn.getEname() + "\" type=\"hidden\">";
        if (formType.startsWith("easyui")) {
            formStr = EASY_TYPE_FORM_STR.replace("#name#", deveObjectColumn.getEname()).replace("#formType#", formType);
            formStr = formStr.replace("#width#", width).replace("#height#", height).replace("#css#", "");
            formStr = formStr.replace("#multiline#", multiline).replace("#dataOptions#",deveObjectColumn.getUiCheck());

            //文本域类型
            if (StringUtils.isNotEmpty(multiline)){
                deveObjectColumn.setFormType("textArea");
            }
        } else if ("file".equals(formType) || "image".equals(formType) || "hidden".equals(formType)) {
            formStr = "<input name=\"" + deveObjectColumn.getEname() + "\" type=\"hidden\">";
        }

        deveObjectColumn.setFormStr(formStr);
    }

    /**
     * 生成验证信息  validType:['email','length[0,20]']
     * @param name
     * @return
     */
    public String generDataOption(int length , String name) {
        String options = "validType:['length[0," + length + "]'" ;
        if(StringUtils.isNotEmpty(name)){
            for (String key : optMap.keySet()){
                if (name.toUpperCase().contains(key.toUpperCase()) ){
                    options += "," + optMap.get(key) ;
                    break;
                }
            }
        }

        options += "]";
        return options ;
    }

}
