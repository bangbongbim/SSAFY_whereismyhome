<template>
    <div class="login-container">
        <div class="login-contents">
            <div class="login-box">
                <p class="login-title">관리자 로그인</p>
                <input type="password" />

                <v-form ref="form" v-model="formValid" lazy-validation>
                    <v-text-field v-model="email" :rules="emailRule" label="E-mail을 입력하세요." outlined clearable @keyup.enter="login">
                    </v-text-field>
                    <v-text-field
                        v-model="password"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="passwordRule"
                        :type="show1 ? 'text' : 'password'"
                        label="Password를 입력하세요"
                        hint="At least 8 characters"
                        @click:append="show1 = !show1"
                        outlined
                        clearable
                        @keyup.enter="login"
                    >
                    </v-text-field>
                </v-form>

                <v-btn @click="login" depressed color="white" outlined text large class="my-1 primary"> 이메일로 로그인하기 </v-btn>
                <v-btn @click="login" depressed color="white" outlined text large class="my-1 primary"> 카카오로 로그인하기 </v-btn>

                <div class="find-box">
                    <span>회원가입</span>
                    <span>아이디/비밀번호 찾기</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// Import the CSS or use your own!
import Vue from 'vue';
import 'vue-toastification/dist/index.css';
import Toast from 'vue-toastification';
import http from '../common/axios';

const options = {
    transition: 'Vue-Toastification__bounce',
    maxToasts: 5,
    newestOnTop: true,
};

Vue.use(Toast, options);
export default {
    data: () => ({
        email: '',
        password: '',
        show1: false,
        formValid: false,
        emailRule: [
            (v) => !!v || '이메일을 입력해주세요.',
            (v) => {
                const replaceV = v.replace(/(\s*)/g, '');
                const pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
                return pattern.test(replaceV) || '이메일 형식으로 입력해주세요';
            },
        ],
        passwordRule: [
            (v) => !!v || '비밀번호를 입력해주세요.',
            (v) => {
                const replaceV = v.replace(/(\s*)/g, '');
                return replaceV.length >= 8 || '8자리 이상 입력해주세요.';
            },
        ],
    }),
    methods: {
        async login() {
            if (!this.formValid) {
                this.$toast.error('아이디 또는 패스워드를 입력 양식을 확인해주세요!', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            } else {
                try {
                    const options = {
                        userEmail: this.email,
                        userPassword: this.password,
                    };
                    let response = await http.post('/login', options);
                    const { data } = response;

                    if (data.result === 'SUCCESS') {
                        /**
                         * ! 로그인 세션 체크 필요
                         */
                        const payload = {
                            ...data,
                            isLogin: true,
                        };
                        sessionStorage.setItem('userInfo', JSON.stringify(payload));
                        this.$store.dispatch('setLogin', payload);
                        this.$toast.success('로그인 되었습니다.', {
                            position: 'bottom-right',
                            timeout: 500,
                            draggablePercent: 0.48,
                        });

                        setTimeout(() => {
                            this.$router.replace('/admin/main');
                        }, 500);
                    }
                } catch (error) {
                    this.$toast.error('아이디 또는 패스워드를 확인해주세요 !', {
                        position: 'bottom-right',
                        timeout: 1800,
                        draggablePercent: 0.48,
                    });
                    console.error(error);
                }
            }
        },
    },
    watch: {
        formValid: function () {
            console.log(this.formValid);
        },
    },
};
</script>

<style scoped lang="scss">
.login-container {
    width: 100%;
    min-height: 100vh;
    height: 100vh;
    background-color: $background;

    .login-contents {
        display: flex;
        justify-content: center;
        align-items: center;
        max-width: 1280px;
        width: 100%;
        height: 100%;
        margin: 0 auto;

        .login-box {
            display: flex;
            flex-direction: column;
            padding: 50px;
            width: 450px;

            .login-title {
                font-size: 30px;
                margin-bottom: 18px;
                text-align: center;
            }
            .find-box {
                margin-top: 5px;
                text-align: right;

                & span {
                    cursor: pointer;
                    font-size: 12px;
                    font-weight: bold;
                }
                & span:first-child::after {
                    content: ' | ';
                }
            }
        }
    }
}
</style>
