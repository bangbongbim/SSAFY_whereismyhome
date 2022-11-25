<!-- eslint-disable vue/no-parsing-error -->
<template>
  <div class="container">
    <div class="contents">
      <h3>나의 관심매물 리스트</h3>
      <div class="cards-box">
        <v-card class="card" v-for="bookmark in bookmarkList" :key="bookmark.bookMarkSeq">
          <v-img :src="bookmark.img" height="200px" class="white--text align-end" @click="moveToDetail(bookmark.houseNo)"> </v-img>
          <v-card-actions>
            <p class="text">{{ bookmark.houseName }}</p>
            <v-spacer></v-spacer>
            <v-btn icon @click="deleteBookmark(bookmark.bookMarkSeq)">
              <v-icon color="warn">mdi-heart</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import "vue-toastification/dist/index.css";
import Toast from "vue-toastification";
import http from "../common/axios";
import util from "@/common/util.js"; //util.js import

const options = {
  transition: "Vue-Toastification__bounce",
  maxToasts: 5,
  newestOnTop: true,
};
Vue.use(Toast, options);
export default {
  data() {
    return {
      src: "https://cdn.vuetifyjs.com/images/cards/plane.jpg",
      bookmarkList: [],
    };
  },
  methods: {
    async getBookmarkList() {
      let { userSeq } = JSON.parse(sessionStorage.getItem("userInfo"));

      try {
        let response = await http.get(`bookmark/${userSeq}`);
        let { data } = response;
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        }

        this.bookmarkList = data.bookmarkList.map((bookmark) => {
          return { ...bookmark, img: require(`../assets/img/apart/${bookmark.img}`) };
        });
        console.log(this.bookmarkList);
      } catch (error) {
        console.log(error);
      }
    },

    async deleteBookmark(bookMarkSeq) {
      try {
        let response = await http.delete(`/bookmark/${bookMarkSeq}`);
        let { data } = response;
        console.log(data);
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        }

        if (data.result === "SUCCESS") {
          this.$toast.success("관심매물이 삭제되었습니다.", {
            position: "bottom-right",
            timeout: 1200,
            draggablePercent: 0.48,
          });
          this.getBookmarkList();
        }
      } catch (error) {
        console.error("error");
      }
    },
    moveToDetail(houseNo) {
      console.log(houseNo);
      this.$router.push({ path: "/detailDeal/" + houseNo });
    },
  },
  created() {
    this.getBookmarkList();
  },
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  min-height: 100vh;
  .contents {
    max-width: 1280px;
    width: 100%;
    margin: 0 auto;
    height: 100%;

    .cards-box {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      flex-wrap: wrap;

      @media (max-width: $mobile) {
        & {
          justify-content: center;
        }
      }

      & .card {
        transition: all 0.3s;
        width: 23%;
        height: 250px;
        margin: 10px;
        @media (max-width: $laptop) {
          & {
            width: 40%;
          }
        }

        @media (max-width: $tablet) {
          & {
            width: 45%;
          }
          .text {
            font-size: 1rem;
          }
        }

        @media (max-width: $mobile) {
          & {
            width: 80%;
          }
        }
      }
      & .text {
        margin-bottom: 0;
        font-size: 1rem;
      }
    }
  }
}
</style>
