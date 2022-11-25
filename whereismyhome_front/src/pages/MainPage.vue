<template>
  <div>
    <header-component></header-component>
    <div class="container-page1">
      <video class="video" autoplay loop muted>
        <source src="../assets/video/test1.mp4" />
      </video>
      <!-- <div class="index-contents">
        <div class="index-copyright-box">
          <p class="copright-text">
            Lorem Ipsum is simply dummy text of the printing and typesetting
            industry.
          </p>
        </div>
      </div> -->
    </div>

    <!-- <div class="recommand-box">
            <p class="contents-title">추천 매물</p>
            <div class="recommand-contents">
                <div class="item-box">
                    <img src="#" alt="" class="item-img" />
                    <p class="item-title">제목</p>
                    <p class="item-description">아파트아프타파느느</p>
                </div>
            </div>
        </div> -->
    <div class="container-page2">
      <div class="contents">
        <div class="left-contents">
          <p class="left-title">인기 부동산 뉴스 TOP3</p>
          <div class="left">
            <div
              class="news-box"
              v-for="(news, index) in newsList"
              :key="index"
              @click="moveNewsDetail(news.url)"
            >
              <div class="news-thumbnail-box">
                <img :src="news.urlToImage" alt="뉴스 썸네일" />
              </div>
              <div class="news-contents-box">
                <p class="news-title">{{ news.title }}</p>
                <p class="news-description" v-html="news.description"></p>
              </div>
            </div>
          </div>
        </div>

        <div class="right-contents">
          <p class="right-title">공지사항</p>
          <div class="right">
            <div
              class="board-box"
              v-for="(board, index) in boardList"
              :key="index"
              style="cursor: pointer"
              @click="moveToDetailPage(board.boardId)"
            >
              <p class="board-title">{{ board.title }}</p>
              <div class="board-info">
                <div>
                  <p class="userName">{{ board.userName }}</p>
                </div>
                <p class="regDt">{{ makeDateStr(board.regDt) }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <footer-component></footer-component>
  </div>
</template>

<script>
import HeaderComponent from "../components/common/HeaderComponent.vue";
import FooterComponent from "../components/common/FooterComponent.vue";
import axios from "axios";
import util from "@/common/util.js"; //util.js import
import Vue from "vue";
import "vue-toastification/dist/index.css";
import Toast from "vue-toastification";

import http from "@/common/axios.js";
const options = {
  transition: "Vue-Toastification__bounce",
  maxToasts: 5,
  newestOnTop: true,
};
Vue.use(Toast, options);
export default {
  data: () => ({
    newsList: [],
    boardList: [],
  }),

  components: {
    HeaderComponent,
    FooterComponent,
  },
  methods: {
    async getNewsList() {
      try {
        const date = new Date();
        const today =
          date.getFullYear() +
          "-" +
          (date.getMonth() + 1) +
          "-" +
          date.getDate();

        const yesterday =
          date.getFullYear() +
          "-" +
          (date.getMonth() + 1) +
          "-" +
          (date.getDate() - 1);
        var url =
          "https://newsapi.org/v2/everything?" +
          "q=부동산&" +
          `from=${yesterday}&` +
          `to=${today}&` +
          "pageSize=3&" +
          "apiKey=d390b1510b12458e86650f46f99d454e";

        let response = await axios.get(url);
        this.newsList = response.data.articles;
        // this.newsList = JSON.parse(sessionStorage.getItem("news"));
        console.log(this.newsList);
        this.newsList = this.newsList.map((news) => {
          let description = news.description;
          if (description.length > 200) {
            description = description.slice(0, 101);
            description += " [...]";

            return { ...news, description: description };
          }
          return news;
        });
        sessionStorage.setItem("news", JSON.stringify(this.newsList));
      } catch (error) {
        console.error(error);
      }
    },
    moveNewsDetail(url) {
      // this.$router.push(url);
      window.open(`${url}`);
      console.log(url);
    },
    async getBoardList() {
      //limit, offset, searchWord 필요
      let params = {
        limit: 4,
        offset: 0,
        searchWord: undefined,
      };

      try {
        let response = await http.get("/boards", {
          params,
        });

        let { data } = response;
        this.boardList = data.list;
        console.log("board list", this.boardList);
      } catch (error) {
        console.error(error);
        this.$toast.error("서버에 문제가 생겼습니다.", {
          position: "bottom-right",
          timeout: 1800,
          draggablePercent: 0.48,
        });
      }
    },
    makeDateStr(regDt) {
      return util.makeDateStr(
        regDt.date.year,
        regDt.date.month,
        regDt.date.day,
        "-"
      );
    },
    moveToDetailPage(boardId) {
      this.$router.push({ path: "/detailBoard/" + boardId });
    },
  },
  created() {
    this.getNewsList();
    this.getBoardList();
  },
};
</script>

<style scoped lang="scss">
@import "../styles/default.scss";
.container-page1 {
  width: 100%;
  max-height: 80vh;
  // background-image: url("../assets/img/article.jpg");
  // background-size: 100% 100%;
  // background-repeat: no-repeat;
  overflow: hidden;

  .video {
    width: 100%;
    max-width: 100%;
  }

  .index-contents {
    padding: 0 20px;
    max-width: 1280px;
    height: 100%;
    margin: 0 auto;
    display: flex;
    flex-direction: column;

    .index-copyright-box {
      padding-top: 10%;
      .copright-text {
        font-size: 3rem;
        font-weight: bold;
        color: #ffff;
      }
    }
  }
}

.search-select-box {
  margin-top: 5%;
  display: flex;
  justify-content: center;
}

/* 추천 매물*/
.container-page2 {
  width: 100%;

  .contents {
    display: flex;
    flex-direction: row;
    max-width: 1280px;
    margin: 25px auto 0 auto;
    height: 100%;

    @media (max-width: $laptop) {
      & {
        flex-direction: column;
      }
    }

    .left-contents {
      width: 50%;
      display: flex;
      flex-direction: column;
      padding: 10px;

      @media (max-width: $laptop) {
        & {
          width: 100%;
          flex-direction: row;
          flex-wrap: wrap;
          padding: 25px;
        }
      }
      .left-title {
        font-weight: bold;
        font-size: 2rem;

        @media (max-width: $laptop) {
          & {
            font-size: 1.5rem;
          }
        }
      }
      .left {
        display: flex;
        flex-direction: column;

        .news-box {
          display: flex;
          flex-direction: row;
          width: 100%;
          height: 130px;
          margin-bottom: 25px;
          text-overflow: ellipsis;
          box-sizing: border-box;
          cursor: pointer;
          transform: all 0.3s;
          border-radius: 5px;
          padding: 10px;

          &:hover {
            background-color: rgba(239, 243, 246, 0.5);
            box-shadow: rgb(0 0 0 / 10%) 0px 8px 8px;
            transition: all 0.3s ease-in-out 0s;
            transform: scale(1.01);
          }

          @media (max-width: $mobile) {
            & {
              height: 120px;
            }
          }

          .news-thumbnail-box {
            width: 20%;
            min-width: 120px;
            border-radius: 10px;
            background-color: $warn;

            @media (max-width: $mobile) {
              & {
                min-width: 120px;
              }
            }

            & img {
              width: 100%;
              height: 100%;
              border-radius: 8px;
            }
          }

          .news-contents-box {
            padding-left: 20px;
            width: 80%;

            & p {
              margin-bottom: 0;
            }
            .news-title {
              font-family: "Roboto" !important;
              font-size: 1.2rem;
              font-weight: bold;
              margin-bottom: 10px;
            }
            .news-description {
              font-size: 0.85rem;

              @media (max-width: 630px) {
                & {
                  display: none;
                }
              }
            }
          }
        }
      }
    }

    .right-contents {
      width: 50%;
      display: flex;
      flex-direction: column;

      @media (max-width: $laptop) {
        & {
          width: 100%;
          flex-direction: row;
          flex-wrap: wrap;
          padding: 25px;
        }
      }
      .right-title {
        font-weight: bold;
        font-size: 2rem;

        @media (max-width: $laptop) {
          & {
            font-size: 1.5rem;
          }
        }
      }
      .right {
        width: 100%;
        .board-box {
          border-radius: 5px;
          display: flex;
          flex-direction: row;
          width: 100%;
          padding: 10px;
          justify-content: space-between;
        }
        .board-box:hover {
          background-color: rgba(239, 243, 246, 0.5);
          box-shadow: rgb(0 0 0 / 10%) 0px 8px 8px;
          transition: all 0.3s ease-in-out 0s;
          transform: scale(1.01);
        }
        .board-title {
          text-overflow: ellipsis;
          font-size: 1.3rem;
        }
        .board-info {
          text-align: right;
          min-width: 66px;

          .userName {
            font-size: 1rem;
          }
          .regDt {
            font-size: 0.8rem;
          }
        }
      }
    }
  }
}
</style>
