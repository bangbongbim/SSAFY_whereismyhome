<template>
    <div class="login-container">
        <div class="login-contents">
            <div class="login-box">
                <p class="login-title">회원가입</p>

                <v-form ref="form" v-model="formValid" lazy-validation>
                    <v-text-field v-model="name" label="이름을 입력하세요" outlined clearable> </v-text-field>
                    <v-text-field v-model="email" :rules="emailRule" label="E-mail을 입력하세요" outlined clearable> </v-text-field>
                    <v-text-field
                        v-model="password"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="passwordRule"
                        :type="show1 ? 'text' : 'password'"
                        label="Password를 입력하세요"
                        @click:append="show1 = !show1"
                        outlined
                        clearable
                        @keyup.enter="login"
                    >
                    </v-text-field>

                    <v-text-field
                        v-model="passwordCheck"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="passwordCheckRule"
                        :type="show1 ? 'text' : 'password'"
                        label="Password를 다시 입력하세요"
                        @click:append="show1 = !show1"
                        outlined
                        clearable
                        @keyup.enter="login"
                    >
                    </v-text-field>
                </v-form>

                <v-btn large depressed outlined text class="my-3 indigo primary" color="white" @click="register"> 회원가입 </v-btn>
            </div>
        </div>
    </div>
</template>

<script>
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
    data() {
        return {
            name: '',
            phone: '',
            email: '',
            show1: false,
            password: '',
            passwordCheck: '',
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

            passwordCheckRule: [
                (v) => !!v || '비밀번호를 다시 입력해주세요.',
                (v) => {
                    const replaceV = v.replace(/(\s*)/g, '');
                    return replaceV.length >= 8 || '8자리 이상 입력해주세요.';
                },
                (v) => {
                    const replaceV = v.replace(/(\s*)/g, '');
                    return replaceV === this.password || '입력하신 패스워드랑 같지 않습니다 !!!';
                },
            ],
        };
    },

    methods: {
        async register() {
            if (!this.formValid) {
                this.$toast.error('입력한 정보를 확인해주세요', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            } else {
                const options = {
                    userName: this.name,
                    userEmail: this.email,
                    userPassword: this.password,
                };

                try {
                    let response = await http.post('/register', options);
                    const { data } = response;
                    console.log(data);

                    if (data.result === 'success') {
                        this.$toast.success('회원가입에 성공했습니다 메인화면으로 이동합니다', {
                            position: 'bottom-right',
                            timeout: 1800,
                            draggablePercent: 0.48,
                        });

                        setTimeout(() => {
                            this.$router.replace('/');
                        }, 1800);
                    }
                } catch (error) {
                    this.$toast.error('회원가입에 실패했습니다', {
                        position: 'bottom-right',
                        timeout: 1800,
                        draggablePercent: 0.48,
                    });
                    console.error(error);
                }
            }
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
        }
    }
}
</style>
