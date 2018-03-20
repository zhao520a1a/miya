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
export const adminCount = () => fetch('/admin/count');
/**
 * 获取用户数量
 */
export const getUserCount = data => fetch('/users/count', data);
/**
 * 获取商品数量
 */
export const getItemCount = data => fetch('/items/count', data);
/**
 * 获取商品类别数量
 */
export const getItemCatCount = data => fetch('/itemCats/count', data);


/**
 * 管理员列表
 */
export const adminList = data => fetch('/admin/all', data);
/**
 * 获取用户列表
 */
export const getUserList = data => fetch('/users/list', data);
/**
 * 获取商品列表
 */
export const getItemList = data => fetch('/items/list', data);
/**
 * 获取商品类别列表
 */
export const getItemCatList = data => fetch('/itemCats/list', data);


/**
 * 获取管理员信息
 */
export const getAdminInfo = data => fetch('/admin/info' ,data);
/**
 * 获取用户信息
 */
export const getUserInfo = user_id => fetch('/users/info' ,{userId: user_id});
/**
 * 获取商品信息
 */
export const getItemInfo = item_id => fetch('/items/info',{itemId: item_id});
/**
 * 获取商品描述信息
 */
export const getItemDescInfo = itemCat_id => fetch('/items/desc' ,{itemCatId: itemCat_id});
/**
 * 获取商品类别详情
 */
export const getCatById = category_id => fetch('/itemCats/info/' ,{categoryId: category_id});
/**
 * 下载本页数据
 */
export const download = data => '/download/' + data;

/**
 * 获取地址信息
 */
export const getAddressById = address_id => fetch('/addresse/' + address_id);


/**
 * 获取用户分布信息
 */
export const getUserCity = () => fetch('/user/city/count');

/**
 * 获取定位城市
 */


// export const cityGuess = () => fetch('/cities', {
//     type: 'guess'
// });

/**
 * 获取搜索地址
 */


// export const searchplace = (cityid, value) => fetch('/pois', {
//     type: 'search',
//     city_id: cityid,
//     keyword: value
// });


/**
 * 添加商铺
 */

export const addShop = data => fetch('/shopping/addShop', data, 'POST');

/**
 * 获取当前店铺商品种类
 */

export const getCategory = restaurant_id => fetch('/shopping/getcategory/' + restaurant_id);


/**
 * 添加商品种类
 */

export const addCategory = data => fetch('/shopping/addcategory', data, 'POST');


/**
 * 添加商品
 */

export const addG = data => fetch('/shopping/addfood', data, 'POST');



/**
 * category 种类列表
 */

export const goodsCategory = (latitude, longitude) => fetch('/shopping/v2/restaurant/category');

/**
 * 获取店铺数量
 */

export const getStoreCount = () => fetch('/stores/count');

/**
 * 获取店铺列表
 */

export const getStoreList = data => fetch('/stores/list', data);

/**
 * 获取店铺详细信息
 */

export const getStoreInfo = restaurant_id => fetch('/stores' + restaurant_id);

/**
 * 更新店铺信息
 */

export const updateStore = data => fetch('/shopping/updateshop', data, 'POST');




/**
 * 删除店铺
 */

export const deleteStore = restaurant_id => fetch('/shopping/restaurant/' + restaurant_id, {}, 'DELETE');

/**
 * 获取menu列表
 */

export const getMenu = data => fetch('/shopping/v2/menu', data);

/**
 * 更新商品信息
 */

export const updateFood = data => fetch('/shopping/v2/updatefood', data, 'POST');

/**
 * 删除商品
 */

export const deleteFood = food_id => fetch('/shopping/v2/food/' + food_id, {}, 'DELETE');


/**
 * 获取订单列表
 */

export const getOrderList = data => fetch('/bos/orders', data);

/**
 * 获取订单数量
 */

export const getOrderCount = data => fetch('/bos/orders/count', data);


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
