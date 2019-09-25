package com.xlauch.core.config.errorcodemsg;

import com.xlauch.core.exception.BussException;
import com.xlauch.utils.util.FastJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
* 应答工具类
*
* @author hxy
*/
public class ResponseCode {

    private static Logger logger = LoggerFactory.getLogger(ResponseCode.class);

    /**
     * 不允许被实例化
     */
    private ResponseCode(){};

    private final static String CODE = "_code";
    private final static String URL = "_url";
    private final static String MSG = "_msg";
    private final static String RESULT = "_result";

    private final static String ERR_CODE_MSG = "错误码：";

    private final static String SUCCESS = "99999";//成功
    private final static String UN_AUTH = "20001";//未登录
    private final static String SYSTEM_ERROR = "10003";//系统异常情况
    private final static String PARAM_ERROR = "10004";//参数不规范
    private final static String SEAT_SALED_ERROR = "20004";//座位已出售
    private final static String LINE_CHANGE_ERROR = "20005";//线路已修改
    private final static String NEED_REL_ERROR = "20006";//需要购票人进行授权
    private final static String FROZEN_ERROR = "20007";//冻结账户

    /**
     * 成功，无消息
     * @return
     */
    public static Map writeSuccess(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,SUCCESS);
        map.put(MSG, "");
        return map;
    }

    /**
     * 成功，自定义消息
     * @param msg
     * @return
     */
    public static Map writeSuccess(String msg){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,SUCCESS);
        map.put(MSG, msg);
        return map;
    }

    /**
     * 成功，并返回结果
     * @param obj
     * @return
     */
    public static Map writeSuccessResult(Object obj){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,SUCCESS);
        map.put(MSG, "");
        map.put(RESULT,obj);
        return map;
    }

    /**
     * 用于web重定向
     * @param code
     * @param url
     * @return
     */
    public static Map writeFailAndRedirect(String code, String url){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,code);
        map.put(URL, url);
        map.put(MSG, getCodeMsg(code));
        return map;
    }

    /**
     * 输出错误码
     * @param code
     * @return
     */
    public static Map writeFailCode(String code){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, code);
        map.put(MSG, ERR_CODE_MSG + code);
        return map;
    }

    /**
     * 输出错误提示信息
     * @param code
     * @return
     */
    public static Map writeFail(String code){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, code);
        map.put(MSG, getCodeMsg(code));
        return map;
    }
    
    /**
     * 输出错误提示信息
     * @param code
     * @return
     */
    public static String writeFailString(String code){
    	Map<String,Object> map = writeFail(code);
    	return FastJsonUtils.toJSONString(map);
    }

    public static Map writeUnAuth(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, UN_AUTH);
        map.put(MSG, getCodeMsg(UN_AUTH));
        return map;
    }

    public static Map writeSystemError(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,SYSTEM_ERROR);
        map.put(MSG, ERR_CODE_MSG + SYSTEM_ERROR);
        return map;
    }

    public static Map writeParamError(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, PARAM_ERROR);
        map.put(MSG, ERR_CODE_MSG + PARAM_ERROR);
        return map;
    }
    public static Map writeSeatSaledError(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, SEAT_SALED_ERROR);
        map.put(MSG, getCodeMsg(SEAT_SALED_ERROR));
        return map;
    }

    public static Map writeLineChangeError(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, LINE_CHANGE_ERROR);
        map.put(MSG, getCodeMsg(LINE_CHANGE_ERROR));
        return map;
    }
    public static Map writeLineChangeError(Object object){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE, LINE_CHANGE_ERROR);
        map.put(MSG, getCodeMsg(LINE_CHANGE_ERROR));
        map.put(RESULT, object);
        return map;
    }
    public static Map writeNeedRelError(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,NEED_REL_ERROR);
        map.put(MSG, getCodeMsg(NEED_REL_ERROR));
        return map;
    }
    public static Map writefrozenError(String msg){
        String errorMsg = getCodeMsg(FROZEN_ERROR);
        if(StringUtils.isNotBlank(msg)){
            errorMsg = msg;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(CODE,FROZEN_ERROR);
        map.put(MSG,errorMsg);
        return map;
    }

    /**
     * 抛出业务异常 返回json串
     * @param errorCode
     */
    public static void bussException(String errorCode){
        throw new BussException(errorCode);
    }

    /**
     * 从缓存中获取提示信息
     * @param code
     * @return
     */
    public static String getCodeMsg(String code){
       return ErrorCode.getInstance().getValue(code);
    }

}
