import fetch from '@/config/fetch'


/**
 * 登陆
 */
export const login = data => fetch('/admin/login', data, 'POST');

/**
 * 退出
 */
export const signout = () => fetch('/admin/singout');
/**
 * 管理员数量
 */
export const adminCount = () => fetch('/admin/count', "POST");

/**
 * 管理员列表
 */
export const adminList = data => fetch('/admin/all', data);
/**
 * 获取管理员信息
 */
export const getAdminInfo = data => fetch('/admin/info', data);
/**
 * 获取用户数量
 */
export const getUserCount = data => fetch('/users/count', data);
/**
 * 获取用户列表
 */
export const getUserList = data => fetch('/users/list', data);


/**
 * 获取商品数量
 */
export const getItemCount = data => fetch('/items/count', data);
/**
 * 获取商品列表
 */
export const getItemList = data => fetch('/items/list', data);
/**
 * 获取商品描述信息
 */
export const getItemDescInfo = data => fetch('/item/descInfo', data);
/**
 * 获取商品描述信息
 */
export const getItemParam = data => fetch('/item/itemParam', data);
/**
 * 删除商品
 */
export const deleteItem = item_id => fetch('/item/delete/' + item_id, {}, 'DELETE');
/**
 * 添加商品
 */
export const addItem = data => fetch('/item/add', data, 'POST');
/**
 * 修改商品
 */
export const updateItem = data => fetch('/item/update', data, 'POST');

/**
 * 获取商品类别列表
 */
export const getItemCatList = data => fetch('/itemCats/list', data);
/**
 * 获取商品类别数量
 */
export const getItemCatCount = data => fetch('/itemCats/count', data);
/*
* 获得特定的商品类别
*/
export const getItemCat = data => fetch('/itemCats/info', data);



/**
 * 删除图片
 */
export const deleteAdminPic = data => fetch('/admin/pic/delete/', data);
export const deleteItemPic = data => fetch('/item/pic/delete/', data);
export const deleteContentPic = data => fetch('/content/pic/delete/', data);

export const getItemPicInfo = data => fetch('/item/pic/get/', data);
export const getContentPicInfo = data => fetch('/content/pic/get/', data);

/**
 * 获取订单数量
 */

export const getOrderCount = data => fetch('/orders/count', data);
/**
 * 获取订单列表
 */
export const getOrderList = data => fetch('/orders/list', data);
/**
 * 获取内容数量
 */

export const getContentCount = data => fetch('/contents/count', data);
/**
 * 获取内容列表
 */
export const getContentList = data => fetch('/contents/list', data);
/*
* 获得特定的商品类别
*/
export const getContentCat = data => fetch('/contentCats/info', data);


/**
 * 删除内容
 */
// export const deleteContent = id => fetch('/content/delete/' + id, {}, 'DELETE');
export const deleteContent = id => fetch('/content/delete/' + id);
/**
 * 添加内容
 */
export const addContent = data => fetch('/content/add', data, 'POST');
/**
 * 修改内容
 */
export const updateContent = data => fetch('/content/update', data, 'POST');


/**
 * 获取menu列表
 */

export const getMenu = data => fetch('/shopping/v2/menu', data);


/**
 * api请求量
 */

// export const apiCount = date => fetch('/statis/api/' + date + '/count');
//
// /**
//  * 所有api请求量
//  */
//
// export const apiAllCount = () => fetch('/statis/api/count');
//
//
// /**
//  * 所有api请求信息
//  */
//
// export const apiAllRecord = () => fetch('/statis/api/all');
//
// /**
//  * 用户注册量
//  */
//
// export const userCount = date => fetch('/statis/user/' + date + '/count');
//
// /**
//  * 某一天订单数量
//  */
//
// export const orderCount = date => fetch('/statis/order/' + date + '/count');
//
//
// /**
//  * 某一天管理员注册量
//  */
//
// export const adminDayCount = date => fetch('/statis/admin/' + date + '/count');
//
