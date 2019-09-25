package com.xlauch.web.config.beetl.tag;

import com.xlauch.web.entity.sys.SysDict;
import com.xlauch.web.service.sys.ISysDictService;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.GeneralVarTagBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 类描述 : 自定义数据字典标签
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/11/20
 */
@Component
@Scope("prototype")
public class DictTag extends GeneralVarTagBinding {


    /**
     * 名称
     */
    private String name ;

    /**
     * 选中值
     */
    private String value ;

    /**
     * 显示类型
     */
    private String showType ;

    /**
     * 是否显示请选择
     */
    private boolean showNull ;

    /**
     * 字典类型
     */
    private String dictType ;

    /**
     * 宽度
     */
    private String width ;

    /**
     * 选择事件
     */
    private String onSelect ;

    /**
     * 扩展属性
     */
    private String dataOptions ;

    /**
     * 字典列表
     */
    private List<SysDict> dictList  ;

    /**
     * 系统字典表 服务类
     */
    @Autowired
    private ISysDictService dictService ;

    @Override
    public void render() {
        //初始化
        init();

        String str = "" ;
        switch (showType.toLowerCase()) {
            case "select": str = showSelect(); break;
            case "radio" : str = showRadio() ; break;
            case "checkbox" : str = showCheckbox() ; break;
            default: str = showSelect();
        }

        try{
            this.ctx.byteWriter.writeString(str);
        }catch (IOException e){

        }
    }

    /**
     * 初始化属性值
     */
    private void init() {
        this.name = getAttr("name" , "") ;
        this.showType = getAttr("showType" , "select") ;
        this.dictType = getAttr("dictCode" , "") ;
        this.width = getAttr("width" , "200") ;
        this.value = getAttr("value" , null) ;
        this.onSelect = getAttr("onSelect" , null) ;
        this.dataOptions = getAttr("dataOptions" , "") ;
        this.dictList = dictService.selectSysDictByCodeCache(dictType);
        this.showNull = Boolean.parseBoolean(getAttr("showNull" , "true"));
    }


    /**
     * 获取属性
     * @param name
     * @param defaultValue
     * @return
     */
    private String getAttr(String name , String defaultValue) {
        if(this.getAttributeValue(name) == null ) {
            return defaultValue ;
        }

        return String.valueOf(this.getAttributeValue(name)) ;
    }


    /**
     * 显示下拉框
     * @return
     */
    private String showSelect() {
        StringBuffer sbf = new StringBuffer() ;
        sbf.append("<select id='").append(name).append("' name='").append(name).append("'  class=\"easyui-combobox\" style=\"width:").append(width).append("px;\" ");
        //sbf.append("<select name='").append(name).append("'  class=\"easyui-combobox\" style=\"width:").append(width).append("px;\" ");
        if(StringUtils.isNotEmpty(dataOptions)){
            sbf.append(" data-options=\"").append(dataOptions).append("\" ");
        }
        sbf.append(">");

        if(showNull) {
            sbf.append("<option value=\"\">请选择</option>");
        }
        if(dictList != null) {
            for (SysDict sysDict : dictList) {
                sbf.append("<option value=\"").append(sysDict.getVal()).append("\" ");
                if(sysDict.getVal().equals(value)){
                    sbf.append(" selected=\"selected\" ");
                }
                sbf.append(" > ").append(sysDict.getName()).append("</option>");
            }
        }
        sbf.append("</select>");
        return sbf.toString() ;
    }


    /**
     * 显示单选框
     * @return
     */
    private String showRadio() {
        StringBuffer sbf = new StringBuffer() ;

        if(dictList != null) {
            for (SysDict sysDict : dictList) {
                sbf.append("&nbsp;<label>").append("<input type=\"radio\" ").append(" name=\"").append(name).append("\"");
                sbf.append(" value=\"").append(sysDict.getVal()).append("\" ");
                if(sysDict.getVal().equals(value)){
                    sbf.append(" checked=\"checked\" ");
                }
                sbf.append("> ");
                sbf.append(sysDict.getName()).append("&nbsp;");
                sbf.append("</label>");
            }
        }
        return sbf.toString() ;
    }

    /**
     * 显示复选框
     * @return
     */
    private String showCheckbox(){
        StringBuffer sbf = new StringBuffer() ;

        if(dictList != null) {
            for (SysDict sysDict : dictList) {
                sbf.append("&nbsp;<label>").append("<input type=\"checkbox\" ").append(" name=\"").append(name).append("\"");
                sbf.append(" value=\"").append(sysDict.getVal()).append("\" ");
                if(isCheckValue(sysDict.getVal())){
                    sbf.append(" checked=\"checked\" ");
                }
                if(StringUtils.isNotEmpty(dataOptions)){
                    sbf.append(dataOptions);
                }
                sbf.append("> ");
                sbf.append(sysDict.getName()).append("&nbsp;");
                sbf.append("</label>");
            }
        }
        return sbf.toString() ;
    }



    /**
     * 判断是否选择
     * @param val
     * @return boolean
     */
    private boolean isCheckValue(String val) {
        if(value == null){
            return false ;
        }
        String[] values = value.split(",");
        for (String s : values) {
            if(s.equals(val)){
                return true ;
            }
        }
        return false ;
    }
}
