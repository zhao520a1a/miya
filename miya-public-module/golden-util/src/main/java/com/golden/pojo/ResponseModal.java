package com.golden.pojo;

import com.google.common.collect.Maps;
import lombok.*;

import java.io.Serializable;
import java.util.List;
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
//    private static final ObjectMapper MAPPER = new ObjectMapper();
   
    // 响应业务状态
    public Integer code;

    // 响应消息
    private String msg;



    public static ResponseModal success() {
        return ResponseModal.builder().code(RespCode.OK.getKey()).msg("").build();
    }

    public static ResponseModal fail() {
        return ResponseModal.builder().code(RespCode.FAILURE.getKey()).msg("").build();
    }

    public static ResponseModal error() {
        return ResponseModal.builder().code(RespCode.SYS_ERROR.getKey()).msg("").build();
    }
    public static ResponseModal successMsg(String msg) {
        return ResponseModal.builder().code(RespCode.OK.getKey()).msg(msg).build();
    }
    public static ResponseModal failMsg(String msg) {
        return ResponseModal.builder().code(RespCode.FAILURE.getKey()).msg(msg).build();
    }
    public static ResponseModal errorMsg(String msg) {
        return ResponseModal.builder().code(RespCode.SYS_ERROR.getKey()).msg(msg).build();
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



