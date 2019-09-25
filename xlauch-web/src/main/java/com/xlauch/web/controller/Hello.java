package com.xlauch.web.controller;


import lombok.Data;

/**
 * <p>
 * 类描述 :
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/12/28
 */
@Data
public class Hello {

    private String name ;

    public String sayHello( ){
        String hello = "hello " + name ;
        System.out.println(hello);
        return hello ;
    }

}
