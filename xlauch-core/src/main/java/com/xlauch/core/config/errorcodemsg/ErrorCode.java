package com.xlauch.core.config.errorcodemsg;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 类描述    : 错误码缓存   <br/>
 * 项目名称  : xlauch 项目<br/>
 * 类名称    : ErrorCode <br/>
 *
 * @author 伊凡  413916057@qq.com<br/>
 *         创建日期: 2017/11/2 9:56  <br/>
 * @version 0.1
 */
public class ErrorCode {

    private Properties propertie = new Properties();
    private InputStreamReader inputFile;

    private static class SingletonHolder {
        public final static ErrorCode INSTANCE = new ErrorCode();
    }

    public static ErrorCode getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 初始化
     */
    public ErrorCode() {

        propertieFileList();
        /**/
    }

    /**
     * 属性list
     */
    private void propertieFileList(){
        String path = null;
        try {
            path = this.getClass().getClassLoader().getResource("/").toURI().getPath()+ "codemsg";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<File> files = enumerateDir(new File(path));
            for(File file : files){
                try {
                    InputStream inputStream = new FileInputStream(file);
                    inputFile = new InputStreamReader(inputStream, "gbk");

                    propertie.load(inputFile);
                    inputFile.close();
                    /*file.getName().split("-");
                    String cacheKeyPre = Global.getErrCodeKeyPre() + getCodeLang(file.getName());
                    cacheCodeMsg(propertie, cacheKeyPre);*/
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
    }

    private String getCodeLang(String fileName){
        String[] arr = fileName.split("-");
        String lang =  arr[arr.length - 1].split("\\.")[0];
        return lang + ":";
    }

    /**
     * 缓存错误码信息至redis
     */
    /*private void cacheCodeMsg(Properties propertie, String cacheKeyPre){
        for(Map.Entry entry : propertie.entrySet()){
            CacheManager.getInstance().set(cacheKeyPre + entry.getKey(), String.valueOf(entry.getValue()));
        }
    }*/

    /**
     * 遍历文件
     * @param dir
     * @return
     */
    public static List<File> enumerateDir(File dir) {

        List fileList = new ArrayList();
        if (dir == null) {

        } else if (dir.isDirectory()) {

            File[] subFiles = dir.listFiles();

            for (File subFile : subFiles) {

                fileList.add(subFile);
                if (subFile.isDirectory()) {
                    fileList.addAll(enumerateDir(subFile));
                }

            }

        } else {
            fileList.add(dir);
        }

        return fileList;
    }

    public String getValue(String key) {
        if (propertie.containsKey(key)) {
            String value = propertie.getProperty(key);// 得到某一属性的值
            return value;
        } else {
            return "";
        }
    }

    public int getIntValue(String key){
        return getIntValue(key, 0);
    }

    public int getIntValue(String key, int defaultValue){
        if (propertie.containsKey(key)) {
            String value = propertie.getProperty(key);// 得到某一属性的值
            try{
                return Integer.parseInt(value);
            }catch(Exception e){
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public boolean getBooleanValue(String key, boolean defaultValue){
        if (propertie.containsKey(key)) {
            String value = propertie.getProperty(key);// 得到某一属性的值
            try{
                return StringUtils.equalsIgnoreCase(value, "true");
            }catch(Exception e){
                return defaultValue;
            }
        }
        return defaultValue;
    }

}
