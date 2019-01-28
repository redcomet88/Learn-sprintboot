package com.chensi.yghy.util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import java.util.Date;
import java.util.List;

public class JSON {


    private static String dateFormat="yyyy-MM-dd HH:mm:ss";
    /**
     * @desc 不推荐使用此方法
     * @param obj
     * @return
     */
    public static String Encode(Object obj) {
        if (obj == null || obj.toString().equals("null"))
            return null;
        if (obj != null && obj.getClass() == String.class) {
            return obj.toString();
        }
        JSONSerializer serializer = new JSONSerializer();
        serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Date.class);
//        serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Timestamp.class);
        return serializer.deepSerialize(obj);
    }
    /**
     * @desc 不推荐使用此方法
     * @param obj
     * @return
     */
    public static String EnJgcode(Object obj) {
        if (obj == null || obj.toString().equals("null"))
            return null;
        if (obj != null && obj.getClass() == String.class) {
            return obj.toString();
        }
        JSONSerializer serializer = new JSONSerializer();
        serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Date.class);
//        serializer.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), Timestamp.class);
        return serializer.deepSerialize(obj);
    }

    /**
     * @desc 不推荐使用此方法
     * @param json
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Object Decode(String json) {
        if (StringUtil.isEmpty(json))
            return "";
        JSONDeserializer deserializer = new JSONDeserializer();
        // deserializer.use(String.class, new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"));
        Object obj = deserializer.deserialize(json);
        if (obj != null && obj.getClass() == String.class) {
            return Decode(obj.toString());
        }
        return obj;
    }

    public static JSONObject JSONObjectDecode(String json) {
        if (json == null || "".equals(json.toString())) {
            return null;
        }
        JSONObject obj = JSONObject.fromObject(json);
        return obj;
    }

    public static JSONArray JSONArrayDecode(String json) {
        if (json == null || "".equals(json.toString())) {
            return null;
        }

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object o, String key  , Object value) {
                if (value == null || JSONNull.getInstance().equals(value) ||"\"null\"".equals(value)) {
                    return true;
                }
                return false;
            }
        });
        JSONArray obj = JSONArray.fromObject(json,jsonConfig);

        return obj;
    }


    public static String fastjsonEncode(Object obj){
        return com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(obj, dateFormat);
    }



    public static String EncodeWithDateFormat(Object obj, String dateFormat) {
        return com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(obj, dateFormat);
    }

    /**
     * @Description
     *  反序列化能够自动识别如下日期格式：
        ISO-8601日期格式
        yyyy-MM-dd
        yyyy-MM-dd HH:mm:ss
        yyyy-MM-dd HH:mm:ss.SSS
        毫秒数字
        毫秒数字字符串
        .NET JSON日期格式
        new Date(1982932381111)
     * @param json
     * @param clazz
     * @param <T>
     * @return  T
     */
    public static  <T> T Decode(String json,Class<T> clazz){
        return com.alibaba.fastjson.JSON.parseObject(json, clazz);
    }



    public static <T> List<T>  Decode2Array(String jsonString, Class<T> clazz){
        return com.alibaba.fastjson.JSONArray.parseArray(jsonString, clazz);
    }

}
