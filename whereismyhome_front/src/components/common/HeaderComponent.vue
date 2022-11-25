<template>
    <div>
        <header ref="headerRef">
            <div class="contents">
                <div>
                    <router-link to="/" class="logo"> <img src="../../assets/logo.png" alt="" /></router-link>
                </div>
                <nav>
                    <ul>
                        <li v-if="$store.state.user.isLogin" class="list-element">
                            <router-link to="/deal">실거래 조회</router-link>
                        </li>
                        <li v-if="!$store.state.user.isLogin" class="list-element">
                            <router-link to="/login">로그인</router-link>
                        </li>
                        <li v-if="!$store.state.user.isLogin" class="list-element">
                            <router-link to="/register">회원가입</router-link>
                        </li>
                        <li v-if="$store.state.user.isLogin" class="list-element">
                            <router-link to="/mypage">마이페이지</router-link>
                        </li>
                        <li v-if="$store.state.user.isLogin" class="list-element" @click="logout">
                            <router-link to="#">로그아웃</router-link>
                        </li>

                        <!-- <li>
              <v-avatar size="40">
                <img
                  src="https://cdn.vuetifyjs.com/images/john.jpg"
                  alt="John"
                />
              </v-avatar>
            </li> -->
                    </ul>
                </nav>
            </div>
        </header>
        <hamburger-component v-on:toggle="toggle" :checkMobile="checkMobile"></hamburger-component>
        <div ref="mobileMenu" class="mobile-menu-container" @click="closeMobile">
            <div class="mobile-menu-outer">
                <div class="mobile-menu-contents">
                    <ul>
                        <li class="list-element">
                            <router-link to="/">홈으로 가기</router-link>
                        </li>
                        <li v-if="$store.state.user.isLogin" class="list-element">
                            <router-link to="/deal">실거래 조회</router-link>
                        </li>
                        <li v-if="!$store.state.user.isLogin">
                            <router-link to="/login">로그인</router-link>
                        </li>
                        <li v-if="!$store.state.user.isLogin" class="list-element">
                            <router-link to="/register">회원가입</router-link>
                        </li>
                        <li v-if="$store.state.user.isLogin" class="list-element">
                            <router-link to="/mypage">마이페이지</router-link>
                        </li>
                        <li v-if="$store.state.user.isLogin" class="list-element" @click="logout">
                            <router-link to="#">로그아웃</router-link>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Vue from 'vue';
import 'vue-toastification/dist/index.css';
import Toast from 'vue-toastification';
import http from '@/common/axios';
import HamburgerComponent from './HamburgerComponent.vue';

const options = {
    transition: 'Vue-Toastification__bounce',
    maxToasts: 5,
    newestOnTop: true,
};

Vue.use(Toast, options);
export default {
    components: {
        HamburgerComponent,
    },
    data() {
        return {
            checkMobile: false,
            userInfo: {},
        };
    },
    methods: {
        async logout() {
            try {
                let response = await http.get('/logout');
                let { data } = response;

                if (data.result === 'success') {
                    this.$store.dispatch('resetState');
                    sessionStorage.removeItem('userInfo');
                    this.$refs.mobileMenu.classList.remove('open');
                    this.checkMobile = false;
                    this.$toast.success('로그아웃이 완료되었습니다', {
                        position: 'bottom-right',
                        timeout: 700,
                    });

                    setTimeout(() => {
                        this.$router.push('/');
                    }, 700);
                }
            } catch (error) {
                this.$toast.error('로그아웃에 실패했습니다', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            }
        },
        toggle() {
            this.$refs.mobileMenu.classList.toggle('open');
            this.checkMobile = true;
        },
        closeMobile(e) {
            if (this.$refs.mobileMenu === e.target) {
                this.$refs.mobileMenu.classList.remove('open');
                this.checkMobile = false;
            }
        },
        scrollEvents() {
            const scrollY = window.scrollY;
            if (scrollY > 0) {
                this.$refs.headerRef.style.backgroundColor = 'white';
            } else {
                this.$refs.headerRef.style.backgroundColor = 'transparent';
            }
        },
    },

    mounted() {
        const payload = JSON.parse(sessionStorage.getItem('userInfo'));
        if (payload) this.$store.dispatch('setLogin', payload);
        window.addEventListener('scroll', this.scrollEvents);
    },
    beforeDestroy() {
        window.removeEventListener('scroll', this.scrollEvents);
    },
};
</script>
<style scoped lang="scss">
header {
    transition: all ease-out 0.3s;
    top: 0;
    left: 0;
    position: fixed;
    width: 100%;
    height: 80px;
    // background-color: rgb(255, 255, 255);
    background: transparent;
    box-shadow: 0 2px 5px rgba(57, 63, 72, 0.3);
    z-index: 1000;
    .contents {
        margin: 0 auto;
        max-width: 1280px;
        height: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;

        .logo img {
            width: 64px;
        }
        nav > ul {
            display: flex;
            flex-direction: row;
            margin-bottom: 0;
        }

        li {
            list-style: none;
            padding: 1rem;
        }

        li > a {
            color: #000;
            text-decoration: none;
            font-size: $font-size-title;
        }
    }
}
.mobile-menu-container {
    transition: all 0.5s;
    display: flex;
    justify-content: flex-end;
    position: absolute;
    top: 0;
    right: 0;
    z-index: 5;
    width: 0;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.2);
    visibility: hidden;
    opacity: 0;

    .mobile-menu-outer {
        display: flex;
        flex-direction: column;
        width: 60%;
        height: 100vh;
        background-color: $second;
        padding: 25px;
        z-index: 6;

        .mobile-menu-contents {
            margin-top: 40px;
            width: 100%;

            & ul li {
                list-style: none;
                text-align: right;
                padding: 15px 0;
                & a {
                    color: #000;
                    text-decoration: none;
                    font-size: $font-size-title;
                }
            }
        }
    }
}
.open {
    width: 100%;
    opacity: 1;
    visibility: visible;
}
.close {
    width: 0;
    opacity: 0;
    visibility: hidden;
}
@media (max-width: $laptop) {
    header {
        display: none;
    }
}
</style>
