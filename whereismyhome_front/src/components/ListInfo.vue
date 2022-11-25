<template>
  <div class="item-box" @click="moveToDetailPage" @mouseover="mouseOver" @mouseleave="mouseLeave">
    <!-- to="/"-->
    <div class="info-box">
      <div class="info-img-box">
        <img :src="require(`../assets/img/apart/${info.img}`)" alt="" class="info-img" />
        <!--"require(`../assets/img/${info.img}`)" -->
      </div>
      <div class="content-box">
        <h3>{{ info.aptName }}</h3>
        <div style="height:25px"></div>
        동 : {{ info.dong }}
        <br />
        넓이 : {{ info.area }} ㎡
        <br />
        평수 : {{ parseInt(this.info.area / 3.3) }} 평
        <br />
        가격 : {{ info.dealAmount }} 만원
        <br />
        건축년도 : {{ info.buildYear }}
      </div>
    </div>
    <div class="division-line"></div>
  </div>
</template>

<script>
import { BUS } from "@/bus/EventBus.js";
export default {
  props: ["info", "ind"],
  data() {
    return {
      testImg: require("../assets/img/test.jpg"),
    };
  },
  methods: {
    moveToDetailPage() {
      this.$store.state.deal.lastClickedLng = this.info.lng;
      this.$store.state.deal.lastClickedLat = this.info.lat;
      this.$router.push({ path: "/detailDeal/" + this.info.houseNo });
      //console.log(this.info);
    },
    mouseOver() {
      //console.log("mouseOver",this.info.aptName)
      BUS.$emit("mouseOver", this.info, this.ind);
    },
    mouseLeave() {
      BUS.$emit("mouseLeave", this.info, this.ind);
    },
  },
};
</script>

<style>
.info-box:hover {
  background-color: rgba(239, 243, 246, 0.5);
  box-shadow: rgb(0 0 0 / 10%) 0px 8px 8px;
  transition: all 0.3s ease-in-out 0s;
  transform: scale(1.01);
}
.info-box {
  padding: 20px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease-in-out 0s;
  display: flex;
  height: 250px;
  flex-direction: row;
}
.info-img-box {
  width: 50%;
  display: flex;
  margin: 0 auto;
}
.info-img {
  border-radius: 20px;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.content-box {
  padding: 8px 20px;
  width: 50%;
  display: flex;
  margin: 0px;
  
  border: 0px;
  vertical-align: baseline;
  display: flex;
  flex-direction: column;
}
.division-line {
  width: 100%;
  height: 1px;
  background-color: rgb(234, 232, 232);
  margin: 20px 0px;
}
</style>
