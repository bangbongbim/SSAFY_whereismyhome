<template>
    <div class="container">
        <p class="notice-title">공지사항 관리</p>

        <div class="input-group mb-3 mt-3">
            <input type="text" class="form-control" placeholder="제목 검색" v-model="searchWord" @keydown.enter="boardList" />
            <v-btn depressed @click="boardList">Search</v-btn>
        </div>

        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">작성자</th>
                    <th scope="col">제목</th>
                    <th scope="col">작성일자</th>
                    <th scope="col">조회수</th>
                </tr>
            </thead>
            <tbody id="boardTbody">
                <tr v-for="(board, index) in list" :key="index" style="cursor: pointer" @click="boardDetail(board.boardId)">
                    <td>{{ board.boardId }}</td>
                    <td>{{ board.userName }}</td>
                    <td>{{ board.title }}</td>
                    <td>{{ board.regDt.date | makeDateStr('.') }}</td>
                    <td>{{ board.readCount }}</td>
                </tr>
            </tbody>
        </table>

        <!-- pagination component, 4개의 props필요 -->
        <PaginationUI
            v-bind:listRowCount="listRowCount"
            v-bind:totalListItemCount="totalListItemCount"
            v-bind:pageLinkCount="pageLinkCount"
            v-bind:currentPageIndex="currentPageIndex"
            v-on:call-parent-move-page="movePage"
        >
        </PaginationUI>
        <button class="btn btn-primary" type="button" @click="showInsertModal">글쓰기</button>
        <insert-modal v-on:call-parent-insert="closeAfterInsert"></insert-modal>
        <detail-modal
            v-bind:board="board"
            v-on:call-parent-change-to-update="changeToUpdate"
            v-on:call-parent-change-to-delete="changeToDelete"
        ></detail-modal>
        <update-modal v-bind:board="board" v-on:call-parent-update="closeAfterUpdate"></update-modal>
    </div>
</template>

<script>
import http from '@/common/axios.js';
import util from '@/common/util.js'; //util.js import
import PaginationUI from './PaginationUI.vue';

import InsertModal from './InsertModal.vue'; //vue 컴포넌트
import DetailModal from './DetailModal.vue'; //vue 컴포넌트
import UpdateModal from './UpdateModal.vue';

import { Modal } from 'bootstrap'; //vue 컴포넌트에서 bootstrap modal을 사용하기 위함

import Vue from 'vue';
import Toast from 'vue-toastification';
const options = {
    transition: 'Vue-Toastification__bounce',
    maxToasts: 5,
    newestOnTop: true,
};
Vue.use(Toast, options);
export default {
    components: { PaginationUI, InsertModal, DetailModal, UpdateModal },
    data() {
        return {
            limit: 10,
            offset: 0,
            searchWord: '',
            list: [],

            totalListItemCount: 0,
            listRowCount: 10, //한페이지에 보여줄갯수
            pageLinkCount: 10, //한번에보여줄 페이지 버튼수
            currentPageIndex: 1,

            //modal
            insertModal: null, //bootstrap modal객체 할당(ui 컴포넌트를 전달)
            detailModal: null,
            updateModal: null,

            // detail
            board: {
                boardId: 0,
                title: '',
                content: '',
                userName: '',
                regDate: '', // 백엔드로부터 받는 dto는 regDt만 받는데 이를 분해해서 regDate, regTime로 나누려는 생각
                regTime: '',
                readCount: 0,
                fileList: [],
                sameUser: false,
            },
        };
    },
    methods: {
        async authGetBoard() {
            let response = await http.get('/boards/auth');
            console.log(response);
            if (response.data == 'SUCCESS') {
                this.boardList();
            } else {
                this.$router.push('/404');
            }
        },
        async boardList() {
            //limit, offset, searchWord 필요
            let params = {
                limit: this.limit,
                offset: this.offset,
                searchWord: this.searchWord,
            };

            try {
                let response = await http.get('/boards', {
                    params,
                });

                let { data } = response;
                console.log(data);
                if (data.result == 'login' || data.result == 'noAuthorized') {
                    //세션 타임아웃
                } else {
                    this.list = data.list;
                    this.totalListItemCount = data.count;
                }
            } catch (error) {
                console.error(error);
                this.$toast.error('서버에 문제가 생겼습니다.', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            }
        },
        movePage(pageIndex) {
            this.offset = (pageIndex - 1) * this.listRowCount;
            this.currentPageIndex = pageIndex;
            this.boardList();
        },
        showInsertModal() {
            this.insertModal.show();
        },
        closeAfterInsert() {
            this.insertModal.hide();
            this.boardList();
        },
        async boardDetail(boardId) {
            // 백엔드 요청 - 결과
            // DetailModal <= 결과 : data 항복에 board객체를 추가하고 props로 DetailModal에 넘겨준다. 백엔드 요청 결과를 data의
            // data의 board를 변경하면
            // DetailModal show

            try {
                let response = await http.get(`/boards/${boardId}`);
                let { data } = response;
                console.log(data);

                console.log(data.dto);
                // 날짜, 시간 분리
                let { regDt } = data.dto;
                let boardNew = {
                    regDate: util.makeDateStr(regDt.date.year, regDt.date.month, regDt.date.day, '-'),

                    regTime: util.makeTimeStr(regDt.time.hour, regDt.time.minute, regDt.time.second, ':'),
                    ...data.dto, // 3 dot operator rest
                };

                // 현재 board 교체
                this.board = boardNew;
                this.detailModal.show();
            } catch (error) {
                console.log(error);
                this.$toast.error('서버에 문제가 생겼습니다.', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            }
        },
        closeDetailModal() {
            this.detailModal.hide();
        },

        changeToUpdate() {
            this.detailModal.hide();
            this.updateModal.show();
        },

        closeAfterUpdate() {
            this.updateModal.hide();
            this.boardList();
        },
        changeToDelete() {
            this.detailModal.hide();
            var $this = this;
            this.$alertify.confirm(
                '삭제하시겠습니까?',
                function () {
                    $this.boardDelete();
                },
                function () {
                    console.log('삭제 취소함');
                }
            );
        },
        async boardDelete() {
            try {
                let response = await http.delete('/boards/' + this.board.boardId);
                let { data } = response;
                if (data == 'login') {
                    //세션 타임아웃
                    this.$router.push('/login');
                } else if (data.reslt == 'noAuthorized') {
                    this.$toast.error('권한이 없습니다.', {
                        position: 'bottom-right',
                        timeout: 1800,
                        draggablePercent: 0.48,
                    });
                } else {
                    this.$toast.success('삭제가 완료 되었습니다.', {
                        position: 'bottom-right',
                        timeout: 1000,
                        draggablePercent: 0.48,
                    });
                    this.boardList();
                }
            } catch (error) {
                console.error(error);
                this.$toast.error('서버에 문제가 생겼습니다.', {
                    position: 'bottom-right',
                    timeout: 1800,
                    draggablePercent: 0.48,
                });
            }
        },
    },
    created: function () {
        this.authGetBoard();
    },
    mounted() {
        //insertModal에 객체(insetModal.vue에서)를 가져와 할당해줌
        this.insertModal = new Modal(document.querySelector('#insertModal'));
        this.detailModal = new Modal(document.querySelector('#detailModal'));
        this.updateModal = new Modal(document.querySelector('#updateModal'));
    },
    filters: {
        makeDateStr: function (date, type) {
            return util.makeDateStr(date.year, date.month, date.day, type);
        },
    },
};
</script>

<style scoped lang="scss">
.container {
    margin-top: 80px;
    max-width: 1280px;
    min-height: 80vh;

    & .notice-title {
        text-align: center;
        margin-top: 50px;
        font-size: 2rem;
    }
}
</style>
