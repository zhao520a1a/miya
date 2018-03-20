/**
 * 配置编译环境和线上环境之间的切换
 *
 * baseUrl: 发送请求的域名地址
 * routerMode: 路由模式
 */
let baseUrl = '';
let routerMode = 'history';
// let baseImgPath = 'http://images.cangdu.org/';
let baseImgPath = '';

if (process.env.NODE_ENV == 'development') {
	//baseUrl = 'http://cangdu.org:8001';
    baseUrl = 'http://localhost:8002';
}else{
	// baseUrl = 'http://cangdu.org:8001';
	baseUrl = 'http://localhost:8002';
}

export {
	baseUrl,
    baseImgPath,
	routerMode,
}
