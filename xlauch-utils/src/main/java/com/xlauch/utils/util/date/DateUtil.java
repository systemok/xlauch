package com.xlauch.utils.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * 	类描述 : 日期工具类
 * </p>
 *
 * @author 伊凡
 * @since 2017/11/27
 * @version 0.1
 */ 
 
public class DateUtil {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	private static DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static TimeZone zone = TimeZone.getTimeZone("GMT+8");

	/**
	 * 把字符串变成日期格式
	 * @param dateStr
	 * @return Date
	 */
	public static Date stringToDate(String dateStr) {
		String formatStr = "yyyy-MM-dd";
		if (dateStr.length() > 10) {
			formatStr = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	

	/**
	 * 格式化日期
	 * @param d
	 * @return String
	 */
	public static String formatDate(Date d){
		
		if(d == null){
			return null ;
		}

		String val = "";
		return cn.hutool.core.date.DateUtil.formatDate(d);
	}
	
	/**
	 * 格式化日期
	 * @param d
	 * @return String
	 */
	public static String formatDateToString(Date d){
		
		if(d == null){
			return null ;
		}
		 
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String val = "''";
		try {
			val = "'"+ sd.format(d)+"'";
		} catch (Exception e) {
			sd = new SimpleDateFormat("yyyy-MM-dd");
			val = "'"+ sd.format(d)+"'";
		}
		return val;
	}
	
	
	/**
	 * 字符串转换到时间格式
	 * @param dateStr 需要转换的字符串
	 * @return Date 返回转换后的时间
	 * @throws ParseException 转换异常
	 */
	public static java.sql.Date StringToSQLDate(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (Exception e) {
			return null ;
		}
		java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
		return sqlDate;
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getTime() {
		Date date = new Date();
		df2.setTimeZone(zone);
		return df2.format(date);
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDate() {
		Date date = new Date();
		df.setTimeZone(zone);
		return df.format(date);
	}
	
	
	 /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }    
      
   /** 
	*字符串的日期格式的计算 
	*/  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
    	smdate = smdate.replaceAll("-", "").replaceAll("/", "");
    	bdate = bdate.replaceAll("-", "").replaceAll("/", "");
    	
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    } 
    
    
    public static void main(String[] args) throws Exception {
		String begin = "20160813";
		String end = "2016/08/19";
		
		System.out.println(daysBetween(begin, end));
	}
    
}
