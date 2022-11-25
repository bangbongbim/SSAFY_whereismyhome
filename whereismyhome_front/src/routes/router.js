import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store/store';

Vue.use(VueRouter);

import {
    MainPage,
    LoginPage,
    RegisterPage,
    ModifyUserInfoPage,
    DealPage,
    DetailDealPage,
    Error404Page,
    BoardPage,
    DetailBoardPage,
    AdminLoginPage,
    AdminMainPage,
    MyPage,
} from '../pages/index';

export default new VueRouter({
    mode: 'history',

    routes: [
        {
            path: '/',
            components: {
                default: MainPage,
            },
        },

        {
            path: '/login',
            components: {
                default: LoginPage,
            },
        },

        {
            path: '/register',
            components: {
                default: RegisterPage,
            },
        },
        {
            path: '/modifyUserInfo',
            components: {
                default: ModifyUserInfoPage,
            },
            beforeEnter: (to, from, next) => {
                console.log(store.state.user.isLogin);
                if (!store.state.user.isLogin) {
                    next('/login');
                } else {
                    return next();
                }
            },
        },
        {
            path: '/deal',
            components: {
                default: DealPage,
            },
            beforeEnter: (to, from, next) => {
                console.log(store.state.user.isLogin);
                if (!store.state.user.isLogin) {
                    next('/login');
                } else {
                    return next();
                }
            },
        },
        {
            path: '/detailDeal/:houseNo',
            components: {
                default: DetailDealPage,
            },
            props: true,
            beforeEnter: (to, from, next) => {
                console.log(store.state.user.isLogin);
                if (!store.state.user.isLogin) {
                    next('/login');
                } else {
                    return next();
                }
            },
        },
        {
            path: '/404',
            components: {
                default: Error404Page,
            },
            props: true,
        },
        {
            path: '/board',
            components: {
                default: BoardPage,
            },
            props: true,
        },
        {
            path: '/detailBoard/:boardId',
            components: {
                default: DetailBoardPage,
            },
            props: true,
        },
        {
            path: '/admin',
            components: {
                default: AdminLoginPage,
            },
        },
        {
            path: '/admin/main',
            components: {
                default: AdminMainPage,
            },
        },
        {
            path: '/mypage',
            components: {
                default: MyPage,
            },
            beforeEnter: (to, from, next) => {
                console.log(store.state.user.isLogin);
                if (!store.state.user.isLogin) {
                    next('/login');
                } else {
                    return next();
                }
            },
        },
        {
            path: '/:pathMatch(.*)*',
            redirect: '/404',
        },
    ],
});
