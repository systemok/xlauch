package com.xlauch.core.supers.dto;

/**
 * <p>
 * 基础的传输bean
 * </p>
 *
 * @author huangxy
 * @since 2018/1/6 15:51
 * @version 0.1
 */
public class SuperDto {

    private String object; //base64编码的json字符串

    private String sign;   //签名

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
