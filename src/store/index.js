import Vue from 'vue'
import Vuex from 'vuex'
import {getAdminInfo} from '@/api/getData'

Vue.use(Vuex)

//初始化状态
const state = {
    adminInfo: {
        id: '-1',
        username: 'TEST ',
        phone: 'TEST',
        email: 'TEST',
        create_time:'',
        admin:'TEST',
        avatar: 'http://120.78.222.191/group1/M00/00/00/wKgBMFqfpXWAOjMlAANajfAuqWs627.jpg'
    },
};

//在这做状态变更
const mutations = {
    saveAdminInfo(state, adminInfo) {
        state.adminInfo = adminInfo;
        window.localStorage.setItem('adminInfo',adminInfo);
    }
};

//对外操作状态的接口
const actions = {

    async setAdminData({commit}, user_id) {
        // alert("获得管理员信息getAdminData-username:" + userId);
        try {
            const res = await getAdminInfo({userId:user_id});
            if (res.responseModal.code == '1') {
                console.log(res.responseModal.msg);
                alert(JSON.stringify(res.data));
                commit('saveAdminInfo', res.data);
            } else {
                throw new Error(res)
            }
        } catch (err) {
            console.log('您尚未登陆或者session失效')
        }
    },
    async getAdminData({commit}) {
        // alert("获得管理员信息getAdminData-username:" + userId);
        try {
            return this.store.state.adminInfo;
        } catch (err) {
            console.log('您尚未登陆或者session失效')
        }
    },


};

export default new Vuex.Store({
	state,
	actions,
	mutations,
})
