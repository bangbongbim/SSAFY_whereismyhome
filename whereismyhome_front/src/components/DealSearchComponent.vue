<template>
  <div class="search-container">
    <div class="search-select-box">
      <!--
      <select name="" id="" class="search-select">
        <option value="" default>시구군을 선택하세요</option>
      </select>-->
      <v-select
        v-model="$store.state.deal.selectedSigugun"
        @change="sigugunSelect()"
        class="search-select"
        :items="siguguns"
        filled
        label="시군구를 선택하세요"
        outlined
      ></v-select>
      <v-select v-model="$store.state.deal.selectedDong" class="search-select" :items="dongs" filled label="동을 선택하세요" outlined></v-select>
    </div>
    <div class="search-input-box">
      <v-text-field v-model="searchWord" label="아파트명" append-outer-icon="mdi-map-marker"></v-text-field>
    </div>
    <v-btn elevation="2" @click="getDealInfo()">검색</v-btn>
  </div>
</template>

<script>
import http from "@/common/axios.js";
import util from "@/common/util.js"; //util.js import

import Vue from "vue";
import "vue-toastification/dist/index.css";
import Toast from "vue-toastification";

const options = {
  transition: "Vue-Toastification__bounce",
  maxToasts: 5,
  newestOnTop: true,
};
Vue.use(Toast, options);

export default {
  data() {
    return {
      siguguns: [
        "강남구",
        "강동구",
        "강북구",
        "강서구",
        "관악구",
        "광진구",
        "구로구",
        "금천구",
        "노원구",
        "도봉구",
        "동대문구",
        "동작구",
        "마포구",
        "서대문구",
        "서초구",
        "성동구",
        "송파구",
        "양천구",
        "영등포구",
        "용산구",
        "은평구",
        "종로구",
        "중구",
        "중랑구",
      ],
      dongs: [],
      searchWord: undefined,
    };
  },
  methods: {
    sigugunSelect() {
      this.$store.state.deal.selectedDong = this.selectedDong;
      this.getDongs();
    },
    async getDongs() {
      //var $this = this;
      try {
        let { data } = await http.get("/region/dong/" + this.$store.state.deal.selectedSigugun);
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        } else {
          this.dongs = data.map((value) => {
            return value.name;
          });
          console.log(this.dongs);
        }
      } catch (error) {
        console.log(error);
      }
    },
    async getDealInfo() {
      console.log(this.$store.state.deal.selectedSigugun, this.$store.state.deal.selectedDong);

      try {
        let searchObj = {
          sigugun: this.$store.state.deal.selectedSigugun,
          dong: this.$store.state.deal.selectedDong,
          searchWord: this.searchWord,
        };
        console.log("searchObj", searchObj);
        let { data } = await http.get("/house/search", { params: searchObj });
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        } else {
          console.log(data);
          this.$store.state.deal.searchedDeals = data;
          //this.searchedDeals=data;
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style scoped>
.search-container {
  width: 100%;
  height: 80px;
  background: rgb(255, 255, 255);
  max-width: 1280px;
  display: flex;
  flex-direction: row;
  align-items: center; /*상하정렬*/
  margin: 0 auto;
}

.search-select-box {
  width: 50%;
  background: rgb(255, 255, 255);
  display: flex;
  flex-direction: row;
}

.search-select {
  width: 300px;
  height: 55px;
  border: none;
  background-color: #ffff;
  text-align: center;
  box-shadow: rgb(0 0 0 / 10%) 0px 2px 2px;
  border-radius: 5px;
}

.search-select:first-child {
  margin-right: 1rem;
}

.search-input-box {
  height: 100%;
  width: 50%;
  padding: 5px 25px 0 25px;
}
</style>
