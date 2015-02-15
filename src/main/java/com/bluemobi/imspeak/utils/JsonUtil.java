package com.bluemobi.imspeak.utils;

import com.bluemobi.imspeak.utils.gson.DmsExclusionStrategy;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by wangbin on 14-10-17.
 */
public class JsonUtil {


    /**
     * json字符串转换成对象
     * @param str  json字符串
     * @param type 对象类型
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String str,Type type){
        Gson gson = new GsonBuilder().
                serializeNulls().
                setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return gson.fromJson(str,type);
    }


    public static String obj2Json(Object obj){
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return  gson.toJson(obj);
    }

    /**
     * java对象转换成json字符串
     * @param obj  java对象
     * @param excludes 过滤字段
     * @return
     */
    public static String obj2Json(Object obj,String ... excludes ){
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setExclusionStrategies(strategy)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return  gson.toJson(obj);
    }


    /**
     * java对象转换成json字符串
     * @param obj
     * @param classes 类别数组
     * @param excludes  字符串数组
     * @return
     */
    public static String obj2Json(Object obj,Class[] classes,String... excludes){
        ExclusionStrategy strategy = new DmsExclusionStrategy(excludes,classes);
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setExclusionStrategies(strategy)
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        return  gson.toJson(obj);
    }


    /**
     * java对象转换成json字符串,过滤成api需要的字段值
     * @param obj
     * @param excludes
     * @return
     */
    public static String obj2ApiJson(Object obj,String ... excludes ){
        String json = obj2Json(obj,excludes);
        return  filterJson(json);
    }

    /**
     * 过滤字符串 -> true转换成0，false转换成1,null转换成""或者{}
     * @param json
     * @return
     */
    public static String  filterJson(String json){
        //"image":null
        String lastStr =  json.replaceAll(":true", ":\"0\"")
                .replaceAll(":false", ":\"1\"")
                              .replaceAll("\"image\":null", "\"image\":{}")
                              .replaceAll(":null",":\"\"");

        return lastStr;
    }


    public static void main(String[] args) {


    }


}
