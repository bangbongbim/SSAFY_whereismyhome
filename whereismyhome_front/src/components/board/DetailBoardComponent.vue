<template>
    <div class="container">
        <hr />
        <div class="title-wrapper">
            <div class="title-box">
                <div class="title">
                    <h2 style="color: #326789">[운영]</h2>
                    &nbsp;&nbsp;
                    <h2>{{ detailBoard.title }}</h2>
                </div>
                <div>{{ regDate }} {{ regTime }}</div>
            </div>
            <div class="title-box">
                <div>
                    <h4>{{ detailBoard.userName }}</h4>
                </div>
                <div>
                    <h5>조회수 : {{ detailBoard.readCount }}</h5>
                </div>
            </div>
        </div>
        <div v-if="detailBoard.fileList.length > 0" class="division-line"></div>
        <div class="file-wrapper">
            <div v-for="(file, index) in detailBoard.fileList" :key="index">
                <span class="fileName">{{ file.fileName }}</span>
                &nbsp;&nbsp;
                <a type="button" class="btn btn-outline btn-default btn-xs" v-bind:href="file.fileUrl" v-bind:download="file.fileName"
                    >내려받기</a
                >
            </div>
        </div>
        <hr />
        <div class="content-wrapper">
            <div class="content-box">
                <td v-html="detailBoard.content"></td>
            </div>
        </div>
        <hr />
    </div>
</template>

<script>
import http from '@/common/axios.js';
import util from '@/common/util.js'; //util.js import

export default {
    data() {
        return {
            detailBoard: {},
            regDate: '',
            regTime: '',
        };
    },
    methods: {
        async getDetail() {
            let { data } = await http.get('/boards/' + this.$route.params.boardId);
            this.detailBoard = data.dto;
            console.log(this.detailBoard);
            let regDt = data.dto.regDt;
            this.regDate = util.makeDateStr(regDt.date.year, regDt.date.month, regDt.date.day, '-');
            this.regTime = util.makeTimeStr(regDt.time.hour, regDt.time.minute, regDt.time.second, ':');
            console.log(this.detailBoard.fileList);
        },
    },
    created() {
        this.getDetail();
    },
    mounted() {
        window.scrollTo(0, 0);
    },
};
</script>

<style lang="scss" scoped>
.container {
    margin-top: 80px;
    width: 100%;
    max-width: 1280px;
    min-height: 80vh;
    .title-wrapper {
        margin: 30px 20px 20px;
        display: flex;
        flex-direction: column;

        .title-box {
            width: 100%;
            display: flex;
            flex-direction: row;
            justify-content: space-between;

            .title {
                display: flex;
                flex-direction: row;
            }
        }
    }

    .file-wrapper {
        margin: 0px 20px;
    }

    .content-wrapper {
        margin: 30px 0;
        padding: 20px;
        width: 100%;
        border-radius: 10px;
        background-color: $background;
        min-height: 800px;
        .content-box {
            margin: 0 10px;
        }
    }

    .division-line {
        width: 100%;
        height: 1px;
        background-color: rgb(234, 232, 232);
        margin: 0 0 20px 0;
    }
}
</style>
