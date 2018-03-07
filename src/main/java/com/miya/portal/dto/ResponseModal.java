package com.miya.portal.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


/*
*  相应工具类

### 接口返回参数
| 参数吗        | 类型    |  说明  |
| --------   | -----  | :----: |
| code        | string  | 'OK', 'FAILURE', 'SYS_ERROR' |
| msg        | string      |   说明文字    |
| data        | json      |   json数据    |

#### code说明：
1. 'OK' => 成功
2. 'FAILURE' => 业务失败
3. 'SYS_ERROR' => 系统失败

### 代码示例
	{
		"code": "OK",
		"msg": "查询成功",
		"data": {
			"userCode": "123456789",
			"name": "教主",
			"idCard": "123456789012345678"
		}
	}

	{
		"code": "FAILURE",
		"msg": "登录失败，用户名密码不匹配",
		"data": null,
	}

	{
		"code": "SYS_ERROR",
		"msg": "服务器连接失败",
		"data": null
	}
*
*
*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseModal implements Serializable {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
   
    // 响应业务状态
    public Integer code;

    // 响应消息
    private String msg;


    public enum RespCode {

        OK(1, "成功"),
        FAILURE(2, "失败"),
        SYS_ERROR(3, "系统错误");


        private Integer key;
        private  String description;

        public static Map<Integer, String> kvMap = Maps.newHashMap();

        static {
            for (RespCode type : values()) {
                kvMap.put(type.getKey(), type.getDesc());
            }
        }

        RespCode(Integer key, String description) {
            this.key = key;
            this.description = description;
        }

        public Integer getKey() {
            return key;
        }

        public String getDesc() {
            return description;
        }
    }


    public static ResponseModal success() {
        return ResponseModal.builder().code(RespCode.OK.key).msg("").build();
    }

    public static ResponseModal fail() {
        return ResponseModal.builder().code(RespCode.FAILURE.key).msg("").build();
    }

    public static ResponseModal error() {
        return ResponseModal.builder().code(RespCode.SYS_ERROR.key).msg("").build();
    }
    public static ResponseModal successMsg(String msg) {
        return ResponseModal.builder().code(ResponseModal.RespCode.OK.key).msg(msg).build();
    }
    public static ResponseModal failMsg(String msg) {
        return ResponseModal.builder().code(ResponseModal.RespCode.OK.key).msg(msg).build();
    }
    public static ResponseModal errorMsg(String msg) {
        return ResponseModal.builder().code(RespCode.SYS_ERROR.key).msg(msg).build();
    }

    public static ResponseModal build(Integer code, String msg) {
        return ResponseModal.builder().code(code).msg(msg).build();
    }


//    /**
//     * 将json结果集转化为TaotaoResult对象
//     *
//     * @param jsonData json数据
//     * @param clazz    TaotaoResult中的object类型
//     * @return
//     */
//    public static ResponseModal formatToPojo(String jsonData, Class<?> clazz) {
//        try {
//            if (clazz == null) {
//                return MAPPER.readValue(jsonData, ResponseModal.class);
//            }
//            JsonNode jsonNode = MAPPER.readTree(jsonData);
//            JsonNode data = jsonNode.get("data");
//            Object obj = null;
//            if (clazz != null) {
//                if (data.isObject()) {
//                    obj = MAPPER.readValue(data.traverse(), clazz);
//                } else if (data.isTextual()) {
//                    obj = MAPPER.readValue(data.asText(), clazz);
//                }
//            }
//            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 没有object对象的转化
//     *
//     * @param json
//     * @return
//     */
//    public static ResponseModal format(String json) {
//        try {
//            return MAPPER.readValue(json, ResponseModal.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * Object是集合转化
//     *
//     * @param jsonData json数据
//     * @param clazz    集合中的类型
//     * @return
//     */
//    public static ResponseModal formatToList(String jsonData, Class<?> clazz) {
//        try {
//            JsonNode jsonNode = MAPPER.readTree(jsonData);
//            JsonNode data = jsonNode.get("data");
//            Object obj = null;
//            if (data.isArray() && data.size() > 0) {
//                obj = MAPPER.readValue(data.traverse(),
//                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
//            }
//            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
//        } catch (Exception e) {
//            return null;
//        }
//    }


}



