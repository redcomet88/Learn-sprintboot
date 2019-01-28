package com.chensi.yghy.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class StringUtil {

    /**
     * 判断是否为空
     * @param s 字符串
     * @return true fasle
     */
    public static boolean isEmpty(String s) {
        if (s == null || SystemConstant.BLANK.equals(s.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取请求url的方法名
     * @param url 字符串
     * @return 方法名
     */
    public static String getActionName(String url) {
        if (isEmpty(url)) {
            return SystemConstant.BLANK;
        } else {
            String[] arr = url.split(SystemConstant.SLASH);
            String action = arr[arr.length - 1];
            String actionName = action.replace(SystemConstant.DO, SystemConstant.BLANK);
            return actionName;
        }
    }

    /**
     * 左边补足0
     * @param s 字符串
     * @return 补足0后的字符串
     */
    public static String addZeroLeft(String s, int count) {
        if (s.length() >= count) {
            return s;
        }
        String format = "%0" + count + "d";
        String result = String.format(format, Long.valueOf(s));
        return result;
    }

    /**
     * 右边边补足0
     * @param s 字符串
     * @return 补足0后的字符串
     */
    public static String addZeroRight(String s, int count) {
        if (s.length() >= count) {
            return s;
        }
        String format = "%0" + (count - s.length()) + "d";
        String result = String.format(format, 0);
        return s + result;
    }

    /**
     * 判断是否为null返回字符串
     * @param s 对象
     * @return 字符串
     */
    public static String getString(Object s) {
        if (s == null) {
            return SystemConstant.BLANK;
        } else {
            return s.toString();
        }
    }

    /**
     * 把金额变换为小数点取两位
     * @param s 对象
     * @return 字符串
     */
    public static String getDecimalString(BigDecimal s) {
        DecimalFormat df = new DecimalFormat("0.00");
        String value = df.format(s).toString();
        return value;
    }

    /**
     * 获取UUID
     * @return
     */
    public static String getStringSeq() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }

    /**
     * 替换字符串中的特殊字符
     *
     * @param originalStr
     * @return
     */
    public static String replaceSpecialCharacters(String originalStr) {

        if (isEmpty(originalStr)) {

            return originalStr;

        } else {

            return originalStr.replace("\r\n", "&#10;").replace("\n", "&#13;").replace("\"", "'");
        }
    }

    /**
     * textarea替换字符串中的特殊字符
     *
     * @param originalStr
     * @return
     */
    public static String textareaChange(String originalStr) {

        if (isEmpty(originalStr)) {

            return originalStr;

        } else {

            return originalStr.replaceAll("&","&amp;").replaceAll("\r\n", "&#10;")
                    .replaceAll("\n", "&#13;").replace("\"", "&quot;")
                    .replaceAll("<","&lt;").replaceAll(">","&gt;")
                    .replaceAll(" ","&nbsp;");
        }
    }



    /**
     * @Description 获取请求参数
     * @author 唐振平 @date 2017年12月7日 @reason 新增
     * @param map 请求参数map
     * @return 请求参数拼接后字符串
     */
    public static String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append(SystemConstant.EQUAL);
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                String v = value[0];
                sb.append(v).append(SystemConstant.COMMA).append(SystemConstant.SPACE);
            } else {
                sb.append(Arrays.toString(value)).append(SystemConstant.COMMA).append(SystemConstant.SPACE);
            }
        }
        if (sb.length() > 1) {
            return sb.substring(0, sb.length() - 2);
        } else {
            return sb.toString();
        }
    }
}
