package com.coriander.common.utils;

import org.springframework.util.StringUtils;

/**
 * @author 姓陈的
 * 2023/4/5 10:43
 * 类型转换器
 */
public class Convert {


    /**
     * 转为字符串
     * @param value 被转换的值
     * @param defaultValue 默认值，加入value为null
     * @return
     */
    public static String toStr(Object value,String defaultValue){

        if(value == null){
            return defaultValue;
        }
        if(value instanceof String){
            return (String) value;
        }
        return value.toString();
    }

    /**
     * 转换字符串<br>
     * 如果value为null，则返回null
     * @param value
     * @return
     */
    public static String toStr(Object value){
        return toStr(value,null);
    }

    /**
     * 转换Integer
     * @param value 转换值
     * @param defaultValue 如果value为空，则返回默认值
     * @return
     */
    public static Integer toInt(Object value,Integer defaultValue){
        if(value == null){
            return defaultValue;
        }
        if(value instanceof Integer){
            return (Integer) value;
        }
        if(value instanceof Number){
            return ((Number) value).intValue();
        }
        final String valueStr = toStr(value,null);
        if(StringUtil.isEmpty(valueStr)){
            return defaultValue;
        }
        try{
            return Integer.parseInt(valueStr.trim());
        }catch (Exception e){
            return defaultValue;
        }
    }

    /**
     * 转换Integer
     * @param value 转换值
     * @return
     */
    public static Integer toInt(Object value){
        return toInt(value,null);
    }


    /**
     * 转换为boolean<br>
     * String支持的值为：true、false、yes、ok、no，1,0 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Boolean toBool(Object value, Boolean defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        if (value instanceof Boolean)
        {
            return (Boolean) value;
        }
        String valueStr = toStr(value, null);
        if (StringUtils.isEmpty(valueStr))
        {
            return defaultValue;
        }
        valueStr = valueStr.trim().toLowerCase();
        switch (valueStr)
        {
            case "true":
            case "yes":
            case "ok":
            case "1":
                return true;
            case "false":
            case "no":
            case "0":
                return false;
            default:
                return defaultValue;
        }
    }

    /**
     * 转换为boolean<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean toBool(Object value)
    {
        return toBool(value, null);
    }




}
