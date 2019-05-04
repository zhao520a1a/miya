package com.golden.util;

import com.alibaba.fastjson.JSON;
import java.util.List;

/**
 * Miya商城自定义响应结构
 */
public class JsonUtils {

    // 定义jackson对象
//    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
//    	try {
//			String string = MAPPERR.writeValueAsString(data);
//			return string;
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//    	return null;

        return  JSON.toJSONString(data);
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
//        try {
//            T t = MAPPER.readValue(jsonData, beanType);
//            return t;
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
//        return null;
//        JSON.parse(jsonData, beanType);
        return JSON.parseObject(jsonData, beanType);
    }
    
    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
//    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
//    	try {
//    		List<T> list = MAPPER.readValue(jsonData, javaType);
//    		return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    	return null;
        return JSON.parseArray(jsonData, beanType);
    }
    
}
