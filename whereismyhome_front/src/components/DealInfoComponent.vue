<template>
  <div class="info-wrapper">
    <div class="explanation" v-if="$store.state.deal.searchedDeals.length == 0">
      검색하면 결과가 나와요!
    </div>
    <list-info
      v-for="(value, index) in $store.state.deal.searchedDeals"
      v-bind:key="index"
      v-bind:info="value"
      v-bind:ind="index"
    ></list-info>
    <!--
    <div class="info-box">
      <div class="info-img-box">
        <img :src="testImg" alt="" class="info-img" />
      </div>
      <div class="content-box">
        <p>설명박스</p>
        <p>설명박스</p>
      </div>
    </div>
    <div class="division-line"></div>
    -->
  </div>
</template>

<script>
import ListInfo from "@/components/ListInfo.vue";

export default {
  components: { ListInfo },
  data() {
    return {
      testImg: require("../assets/img/test.jpg"),
    };
  },
};
</script>

<style scoped>
.info-wrapper {
  width: 680px;
  padding: 30px 20px;
  flex-direction: column;
  display: flex;
  overflow: scroll; /*여기서 스크롤만들기*/
  overflow-x: hidden;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}

.info-wrapper::-webkit-scrollbar {
  /*스크롤바 제거*/
  display: none; /* Chrome, Safari, Opera*/
}

.explanation {
  height: 50px;
  text-align: center;
  font-size: 50px;
  color: #e2e2e2;
}
</style>
