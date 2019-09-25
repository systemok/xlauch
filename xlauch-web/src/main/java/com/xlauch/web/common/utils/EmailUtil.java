package com.xlauch.web.common.utils;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.xlauch.utils.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 类描述：邮件工具类
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/14.
 */
@Configuration
public class EmailUtil {

    static SysParamUtils sysParamUtils;

    @Autowired
    public void setSysParamUtils(SysParamUtils sysParamUtils) {
        EmailUtil.sysParamUtils = sysParamUtils;
    }

    public static boolean sendText(String to, String subject, String content, boolean isHtml){
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(sysParamUtils.getSysParamValue("MailHost"));
        mailAccount.setUser(sysParamUtils.getSysParamValue("MailUser"));
        mailAccount.setPass(sysParamUtils.getSysParamValue("MailPass"));
        mailAccount.setPort(TextUtil.getInt(sysParamUtils.getSysParamValue("MailPort")));
        mailAccount.setFrom(sysParamUtils.getSysParamValue("MailUser"));
        mailAccount.setAuth(true);
        MailUtil.send(mailAccount, to, subject, content, isHtml);
        return true;
    }

}
