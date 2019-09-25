package com.xlauch.web.config.beetl.tag;

import com.xlauch.web.config.beetl.BeetlShiroExt;
import com.xlauch.web.entity.sys.SysDict;
import com.xlauch.web.service.sys.ISysDictService;
import org.beetl.core.GeneralVarTagBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 类描述 : 自定义增删改权限标签
 * </p>
 *
 * @author Huangxy
 * @version 0.1
 * @since 2017/11/20
 */
@Component
@Scope("prototype")
public class ButtonPermissionTag extends GeneralVarTagBinding {


    private BeetlShiroExt beetlShiroExt = new BeetlShiroExt();

    /**
     * 按钮操作
     */
    private String href ;

    /**
     * 按钮样式
     */
    private String iconCls ;

    /**
     * 权限
     */
    private String permission;

    @Override
    public void render() {
        //初始化
        init();

        String str = "" ;
        if(beetlShiroExt.hasPermission(this.permission)) {
            str = showA();
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
        this.href = getAttr("href" , "") ;
        this.iconCls = getAttr("iconCls" , "") ;
        this.permission = getAttr("permission" , "") ;
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
     * 显示A标签
     * @return
     */
    private String showA() {
        StringBuffer sbf = new StringBuffer() ;
        sbf.append("<a href='").append(href).append("'  class='easyui-linkbutton' iconCls='").append(iconCls).append("' plain='true'>");
        sbf.append(this.getBodyContent().getBody());
        sbf.append("</a>");
        return sbf.toString() ;
    }

}
