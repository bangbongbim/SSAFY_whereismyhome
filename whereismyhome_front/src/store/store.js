import Vue from 'vue';
import Vuex from 'vuex';
Vue.use(Vuex);

import user from './modules/user';
import deal from './modules/deal';
import loading from './modules/loading';
//import http from "@/common/axios.js";
//import router from "@/routes/router.js";

export default new Vuex.Store({
    state: {},
    mutation: {},
    modules: {
        user,
        deal,
        loading,
    },
});
