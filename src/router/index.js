import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

//模板
const shopList = r => require.ensure([], () => r(require('@/page/shopList')), 'shopList');
const addShop = r => require.ensure([], () => r(require('@/page/addShop')), 'addShop');


const login = r => require.ensure([], () => r(require('@/page/login')), 'login');
const manage = r => require.ensure([], () => r(require('@/page/manage')), 'manage');


const adminList = r => require.ensure([], () => r(require('@/page/adminList')), 'adminList');
const orderList = r => require.ensure([], () => r(require('@/page/orderList')), 'orderList');
const userList = r => require.ensure([], () => r(require('@/page/userList')), 'userList');
const contentCatList = r => require.ensure([], () => r(require('@/page/contentCatList')), 'contentCatList');
const contentList = r => require.ensure([], () => r(require('@/page/contentList')), 'contentList');
const itemCatList = r => require.ensure([], () => r(require('@/page/itemCatList')), 'itemCatList');
const itemList = r => require.ensure([], () => r(require('@/page/itemList')), 'itemList');

const addContent = r => require.ensure([], () => r(require('@/page/addContent')), 'addContent');
const addItem = r => require.ensure([], () => r(require('@/page/addItem')), 'addItem');


const uploadImg = r => require.ensure([], () => r(require('@/page/uploadImg')), 'uploadImg');
const vueEdit = r => require.ensure([], () => r(require('@/page/vueEdit')), 'vueEdit');
const adminSet = r => require.ensure([], () => r(require('@/page/adminSet')), 'adminSet');



const home = r => require.ensure([], () => r(require('@/page/home')), 'home');
const visitor = r => require.ensure([], () => r(require('@/page/visitor')), 'visitor');
const newMember = r => require.ensure([], () => r(require('@/page/newMember')), 'newMember');

const sendMessage = r => require.ensure([], () => r(require('@/page/sendMessage')), 'sendMessage');
const explain = r => require.ensure([], () => r(require('@/page/explain')), 'explain');

const routes = [
	{
		path: '/',
		component: login
		// component: manage
	},	{
		path: '/login',
		component: login
	},
	{
		path: '/manage',
		component: manage,
		name: '',
		children: [{
			path: '',
			component: home,
			meta: [],
		},{
			path: '/addContent',
			component: addContent,
			meta: ['添加数据', '添加内容信息'],
		},{
			path: '/addItem',
			component: addItem,
			meta: ['添加数据', '添加商品信息'],
		},{
            path: '/addShop',
            component: addShop,
            meta: ['添加数据', '添加商铺'],
        },{
            path: '/shopList',
            component: shopList,
            meta: ['添加数据', '商铺列表'],
        },{
			path: '/userList',
			component: userList,
			meta: ['数据管理', '用户列表'],
		},{
			path: '/contentCatList',
			component: contentCatList,
			meta: ['数据管理', '内容分类列表'],
		},{
			path: '/contentList',
			component: contentList,
			meta: ['数据管理', '内容信息列表'],
		},{
			path: '/itemCatList',
			component: itemCatList,
			meta: ['数据管理', '商品分类列表'],
		},{
			path: '/itemList',
			component: itemList,
			meta: ['数据管理', '商品信息列表'],
		},{
			path: '/orderList',
			component: orderList,
			meta: ['数据管理', '订单列表'],
		}, {
			path: '/adminList',
			component: adminList,
			meta: ['数据管理', '管理员列表'],
		},{
			path: '/visitor',
			component: visitor,
			meta: ['图表', '用户分布'],
		},{
			path: '/newMember',
			component: newMember,
			meta: ['图表', '用户数据'],
		},{
			path: '/uploadImg',
			component: uploadImg,
			meta: ['文本编辑', 'MarkDown'],
		},{
			path: '/vueEdit',
			component: vueEdit,
			meta: ['编辑', '文本编辑'],
		},{
			path: '/adminSet',
			component: adminSet,
			meta: ['设置', '管理员设置'],
		},{
			path: '/sendMessage',
			component: sendMessage,
			meta: ['设置', '发送通知'],
		},{
			path: '/explain',
			component: explain,
			meta: ['说明', '说明'],
		}]
	}
]

export default new Router({
	routes,
	strict: process.env.NODE_ENV !== 'production',
})
