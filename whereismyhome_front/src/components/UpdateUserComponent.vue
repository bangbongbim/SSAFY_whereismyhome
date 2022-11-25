<template>
    <div class="login-box">
        <div class="profileImg-box">
            <div class="profileImg">
                <img ref="previewImg" src="../assets/img/article.jpg" />
                <input @change="handleFile" ref="File" type="file" style="display: none" accept="image/*" />
                <v-btn @click="fileUpload" absolute bottom right small class="mx-2 profileModifyBtn" fab dark color="warn">
                    <v-icon dark> mdi-plus </v-icon>
                </v-btn>
            </div>
        </div>

        <v-form ref="form" v-model="formValid" lazy-validation>
            <v-text-field v-model="userName" label="이름을 입력하세요" outlined clearable> </v-text-field>
            <v-text-field v-model="userEmail" :rules="emailRule" label="E-mail을 입력하세요" outlined clearable> </v-text-field>
            <v-text-field
                v-model="currentPassword"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="passwordRule"
                :type="show1 ? 'text' : 'password'"
                label="현재 Password를 입력하세요"
                @click:append="show1 = !show1"
                outlined
                clearable
            >
            </v-text-field>

            <v-text-field
                v-model="newPassword"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="passwordCheckRule"
                :type="show1 ? 'text' : 'password'"
                label="변경할 Password를 입력하세요"
                @click:append="show1 = !show1"
                outlined
                clearable
            >
            </v-text-field>
        </v-form>

        <v-btn large depressed outlined text class="my-3 indigo primary" color="white" @click="modify"> 수정완료 </v-btn>

        <v-dialog v-model="dialog" max-width="400">
            <template v-slot:activator="{ on, attrs }">
                <v-btn large color="warn" dark v-bind="attrs" v-on="on"> 회원 탈퇴 </v-btn>
            </template>
            <v-card>
                <v-card-title class="text-h5"> 구해줘 홈즈 서비스를 탈퇴하시겠습니까 ? </v-card-title>
                <v-card-text>회원 탈퇴시 회원님과 관련된 모든 정보가 삭제되며 복구되지 않습니다. 그래도 탈퇴하시겠습니까? </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="warn " text @click="dialog = false"> 취소하기 </v-btn>
                    <v-btn color="primary" text @click="deleteUser"> 탈퇴하기 </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
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
            userName: this.$store.state.user.userName,
            userEmail: this.$store.state.user.userEmail,
            userProfileImage: this.$store.state.user.userProfileImageUrl,
            currentPassword: '',
            newPassword: '',
            checkPassword: false,
            show1: false,
            formValid: false,
            dialog: false,
            checkDelete: false,
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
                    return replaceV === this.currentPassword || '입력하신 패스워드랑 같지 않습니다 !!!';
                },
            ],
        };
    },
    methods: {
        handleFile(e) {
            const file = e.target.files[0];
            console.log(file.size);

            const reader = new FileReader();
            reader.onload = (e) => {
                if (file.size >= 1048576) {
                    this.$toast.error('파일의 최대 크기는 1MB입니다', {
                        position: 'bottom-right',
                        timeout: 1800,
                        draggablePercent: 0.48,
                    });
                } else {
                    this.userProfileImage = file;
                    this.$refs.previewImg.src = e.target.result;
                }
            };
            reader.readAsDataURL(file);
        },
        fileUpload() {
            this.$refs.File.click();
        },

        async checkCurrentPassword() {
            try {
                const options = {
                    userEmail: this.userEmail,
                    userPassword: this.currentPassword,
                };
                let response = await http.post('/login', options);
                const { data } = await response;
                if (data.result === 'SUCCESS') {
                    console.log('success');
                    this.checkPassword = true;
                }
            } catch (error) {
                this.checkPassword = false;
                // console.log(error);
            }
        },
        async modify() {
            // 입력한 비밀번호와 유저의 비밀번호가 같은지 체크
            await this.checkCurrentPassword();
            if (!this.checkPassword) {
                this.$toast.error('현재 패스워드가 맞지 않습니다', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            } else {
                const formData = new FormData();
                formData.append('userName', this.userName);
                formData.append('userEmail', this.userEmail);
                formData.append('userPassword', this.newPassword);
                formData.append('userProfileImage', this.userProfileImage);

                try {
                    for (let key of formData.keys()) {
                        console.log(key, ':', formData.get(key));
                    }
                    let response = await http.post('/modify', formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                        },
                    });

                    let { data } = response;
                    if (data.result === 'success') {
                        this.$toast.success('회원정보 수정에 성공했습니다. 메인화면으로 이동합니다', {
                            position: 'bottom-right',
                            timeout: 1800,
                            draggablePercent: 0.48,
                        });

                        setTimeout(() => {
                            this.$router.replace('/');
                        }, 1800);
                    }
                } catch (error) {
                    console.error(error);
                }
            }
        },
        async deleteUser() {
            this.dialog = false;
            try {
                let response = await http.delete('/user');
                let { data } = response;
                if (data.result === 'success') {
                    sessionStorage.removeItem('userInfo');
                    this.$store.dispatch('resetState');
                    this.$toast.success('회원탈퇴에 성공했습니다 메인화면으로 이동합니다', {
                        position: 'bottom-right',
                        timeout: 1500,
                        draggablePercent: 0.48,
                    });

                    setTimeout(() => {
                        this.$router.replace('/');
                    }, 1800);
                }
            } catch (error) {
                this.$toast.error('회원탈퇴에 실패했습니다', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
                console.error(error);
            }
        },
    },
    watch: {
        files: function () {
            console.log(this.files);
        },
        checkPassword: function () {
            console.log(this.checkPassword);
        },
    },

    mounted() {
        console.log('mounted');
        window.scrollTo(0, 0);
    },
};
</script>

<style lang="scss" scoped>
.login-box {
    display: flex;
    flex-direction: column;
    padding: 25px;
    max-width: 450px;
    margin: 0 auto;

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

    .profileImg-box {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-bottom: 20px;

        .profileImg {
            position: relative;
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background-color: $second;
            & img {
                width: 100%;
                height: 100%;
                border-radius: 50%;
            }

            .v-btn--bottom {
                bottom: 0;
            }
            .v-btn--right {
                right: 10px;
            }
        }
    }
}
</style>
