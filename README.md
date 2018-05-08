单点登录 服务模块

//生成token，使用uuid
String token = UUID.randomUUID().toString();		
//清空密码
user.setPassword(null);
//把用户信息保存到redis，key就是token，value就是用户信息;并设置key的过期时间
redisService.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user),SESSION_EXPIRE);
 


