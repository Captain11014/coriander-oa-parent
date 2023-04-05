package com.coriander.common.utils;

/**
 * @author 姓陈的
 * 2023/4/5 11:05
 * 字符串工具类
 */
public class StringUtil {

    /**
     * 空字符串
     */
    private static final String NULLSTR = "";
    private static final String SEPARATOR = "_";


    /**
     * 判断对象是否为空
     * @param object
     * @return 为空：true  不为空：false
     */
    public static boolean isNull(Object object){
        return object == null;
    }


    /**
     * 判断对象是否不为空
     * @param object
     * @return 不为空：true  为空：false
     */
    public static boolean isNotNull(Object object){
        return !isNull(object);
    }


    /**
     * 判断字符串是否为空字符串
     * @param str
     * @return 空字符串：true  非空字符串：false
     */
    public static boolean isEmpty(String str){
        return isNull(str) || NULLSTR.equals(str);
    }

    /**
     * 判断字符串是否为非空字符串
     * @param str
     * @return 非空字符串：true  空字符串：false
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }


    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str){
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }




}
