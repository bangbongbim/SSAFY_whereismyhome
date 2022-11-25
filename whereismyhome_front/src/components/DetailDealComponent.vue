<template>
  <div class="container-page">
    <div class="index-contents">
      <div class="title-box">
        <div class="title-left">
          <div class="title-main">
            <p class="titleName">{{ info.aptName }}</p>
            <v-btn icon @click="bookmarking()" style="margin: 10px 20px">
              <v-icon :color="bookmarkColor" x-large>mdi-heart</v-icon>
            </v-btn>
          </div>
          아파트 | 1,119 세대 | 총 9 동
          <h2>즉시입주가능</h2>
        </div>
        <div class="title-right">
          <!-- 문의 취소 -->
          <div v-if="isAsked">
            <v-dialog v-model="cancelDialog" persistent max-width="400">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="red lighten-5" v-bind="attrs" v-on="on"> 등록된 문의 취소하기 </v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h5"> {{ info.aptName }}에 거래 문의 취소하겠습니까? </v-card-title>
                <v-card-text> {{ proposedPrice }}만원으로 문의했던 내역이 있습니다. 취소하시겠습니까?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="black" text @click="cancelDialogClose"> 돌아가기 </v-btn>
                  <v-btn color="red" text @click="cancelDialogSend"> 취소하기 </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>

          <!-- 문의 모달-->
          <div v-if="isAsked == false">
            <v-dialog v-model="askDialog" persistent max-width="400">
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="blue-grey lighten-5" v-bind="attrs" v-on="on"> 가격 제안 및 문의하기 </v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h5"> {{ info.aptName }}에 거래 문의 하시겠습니까? </v-card-title>
                <v-card-text>{{ $store.state.user.userName }}님의 이름, 전화번호, 문의 내용이 상대방에게 전달됩니다.</v-card-text>
                <v-text-field v-model="askPhone" label="연락처" class="ask-text" placeholder="010-0000-0000"></v-text-field>
                <v-text-field
                  v-model="askPrice"
                  label="희망 보증금(만원)"
                  class="ask-text"
                  prefix="₩"
                  :rules="[() => !isNaN(askPrice) || '숫자만 입력하세요']"
                ></v-text-field>
                <v-textarea
                  v-model="askText"
                  outlined
                  name="input-7-4"
                  label="문의 사항"
                  placeholder="예) 내일 이 매물 투어할 수 있나요?"
                  class="ask-text"
                ></v-textarea>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="black" text @click="askDialogClose"> 취소 </v-btn>
                  <v-btn color="black" text @click="askDialogSend"> 문의 </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
          <!-- 문의 모달-->
          <h3 class="phone-text">1544-1323</h3>
        </div>
      </div>
      <div id="map" ref="map" style="width: 100%; height: 350px"></div>
      <!-- 지도를 표시할 div 입니다 -->
      <div id="roadview" ref="roadview" style="width: 100%; height: 400px"></div>
      <!-- 로드뷰를 표시할 div 입니다 -->

      <div class="main-container">
        <div class="info-container">
          <div class="info-left">
            <p class="font-s1">가격정보</p>
          </div>
          <div class="info-right">
            <h2>최저 보증금 3000만원 월세 510만원</h2>
            <!--{{parseInt(info.dealAmount.replace(/,/g,""))/10 }}-->
            (24개월 계약 기준)
            <h4>최소 12개월부터 24개월까지 자유롭게 계약 기간을 조율하실 수 있습니다.</h4>
            <h4>희망 계약 기간과 보증금이 있으시다면 문의 주세요</h4>
          </div>
        </div>
        <div class="info-container">
          <div class="info-left">
            <p class="font-s1">상세정보</p>
          </div>
          <div class="info-table">
            <div>
              <v-simple-table class="table">
                <template v-slot:default>
                  <tbody>
                    <tr v-for="(value, key) in tableInfo1" v-bind:key="key">
                      <td>
                        <h5>{{ key }}</h5>
                      </td>
                      <td>{{ value }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
            <div>
              <v-simple-table class="table">
                <template v-slot:default>
                  <tbody>
                    <tr v-for="(value, key) in tableInfo2" v-bind:key="key">
                      <td>
                        <h5>{{ key }}</h5>
                      </td>
                      <td>{{ value }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
            </div>
            <br />
          </div>
        </div>
        <div class="info-container">
          <div class="info-left">
            <p class="font-s1">옵션정보</p>
          </div>
          <div class="info-right">
            <h4>포함옵션</h4>
            <div class="option-wrapper">
              <div class="option-box">
                <img src="../assets/img/air_conditioner.svg" />
                &nbsp; 시스템 에어컨
              </div>
              <div class="option-box">
                <img src="../assets/img/refrigerator.svg" />
                &nbsp; 냉장고
              </div>
              <div class="option-box">
                <img src="../assets/img/kimchi_refrigerator.svg" />
                &nbsp; 김치냉장고
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
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
      info: {},
      tableInfo1: {
        아파트명: "",
        평수: "",
        시구군: "",
        지번: "",
      },
      tableInfo2: {
        완공년도: 0,
        면적: "",
        동: "",
        매매금액: 0,
      },
      map: {},
      myPosition: {},
      rvContainer: {},
      rv: {},
      askDialog: false,
      askText: "",
      askPhone: "",
      askPrice: "",

      isAsked: false,
      cancelDialog: false,
      proposedId: 0,
      proposedPrice: 0,

      bookmarkColor: "gray",
    };
  },
  mounted() {},
  methods: {
    setTableInfo() {
      this.tableInfo1.아파트명 = this.info.aptName;
      this.tableInfo2.면적 = String(this.info.area) + "㎡";
      this.tableInfo1.평수 = parseInt(this.info.area / 3.3) + " 평";
      this.tableInfo2.매매금액 = String(this.info.dealAmount) + " 만원";
      this.tableInfo2.완공년도 = this.info.buildYear;
      this.tableInfo1.시구군 = this.info.gugunName;
      this.tableInfo2.동 = this.info.dong;
      this.tableInfo1.지번 = this.info.jibun;
    },
    async getInfo() {
      try {
        let { data } = await http.get("/house/" + this.$route.params.houseNo);
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        } else {
          this.info = data;
          this.setTableInfo();
          console.log(data);
          if (window.kakao && window.kakao.maps) {
            this.initMap();
          } else {
            const script = document.createElement("script");
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" + process.env.VUE_APP_KAKAO_KEY;
            document.head.appendChild(script);
          }
          setTimeout(() => {
            this.initRoadView();
          }, 800);
        }
      } catch (error) {
        console.log(error);
      }
    },
    initMap() {
      //console.log("init!!!!");
      const container = this.$refs.map;

      const options = {
        center: new kakao.maps.LatLng(this.info.lat, this.info.lng),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
      this.myPosition = new kakao.maps.LatLng(this.info.lat, this.info.lng);

      var mMarker = new kakao.maps.Marker({
        position: this.myPosition, // 지도의 중심좌표에 올립니다.
        map: this.map, // 생성하면서 지도에 올립니다.
      });
      var mLabel = new kakao.maps.InfoWindow({
        position: this.myPosition, // 지도의 중심좌표에 올립니다.
        content: this.info.aptName, // 인포윈도우 내부에 들어갈 컨텐츠 입니다.
      });
      mLabel.open(this.map, mMarker);
    },
    initRoadView() {
      var $this = this;
      this.rvContainer = this.$refs.roadview;
      let rv = new kakao.maps.Roadview(this.rvContainer); // 로드뷰 객체 생성
      var rc = new kakao.maps.RoadviewClient(); // 좌표를 통한 로드뷰의 panoid를 추출하기 위한 로드뷰 help객체 생성
      this.rvPosition = new kakao.maps.LatLng(this.info.lat, this.info.lng);
      try {
        rc.getNearestPanoId(this.rvPosition, 250, function (panoid) {
          rv.setPanoId(panoid, $this.rvPosition); //좌표에 근접한 panoId를 통해 로드뷰를 실행합니다.
        });
      } catch (e) {
        console.error(e);
      }

      kakao.maps.event.addListener(rv, "init", function () {
        // 로드뷰에 올릴 마커를 생성합니다.
        var rMarker = new kakao.maps.Marker({
          position: $this.myPosition,
          map: rv, //map 대신 rv(로드뷰 객체)로 설정하면 로드뷰에 올라갑니다.
        });
        rMarker.setAltitude(6); //마커의 높이를 설정합니다. (단위는 m입니다.)
        rMarker.setRange(100); //마커가 보일 수 있는 범위를 설정합니다. (단위는 m입니다.)

        // 로드뷰에 올릴 장소명 인포윈도우를 생성합니다.
        var rLabel = new kakao.maps.InfoWindow({
          content: $this.info.aptName,
        });
        rLabel.setRange(100); //마커가 보일 수 있는 범위를 설정합니다. (단위는 m입니다.)
        rLabel.open(rv, rMarker); // open시 마커를 넣어주면, 마커의 altitude와 position값을 모두 따라 갑니다.

        // 로드뷰 마커가 중앙에 오도록 로드뷰의 viewpoint 조정합니다.
        var projection = rv.getProjection(); // viewpoint(화면좌표)값을 추출할 수 있는 projection 객체를 가져옵니다.

        // 마커의 position과 altitude값을 통해 viewpoint값(화면좌표)를 추출합니다.
        var viewpoint = projection.viewpointFromCoords(rMarker.getPosition(), rMarker.getAltitude());
        rv.setViewpoint(viewpoint); //로드뷰에 뷰포인트를 설정합니다.
      });
    },

    askDialogClose() {
      this.askDialog = false;
      this.askText = "";
      this.askPhone = "";
      this.askPrice = "";
    },
    askDialogSend() {
      this.sendAsk();
      this.askDialog = false;
    },

    cancelDialogClose() {
      this.cancelDialog = false;
    },
    cancelDialogSend() {
      this.cancelAsk();
      this.cancelDialog = false;
    },

    async sendAsk() {
      console.log("문의내용 전송하기", this.info.houseNo);
      const options = {
        houseNo: this.info.houseNo,
        phone: this.askPhone,
        ask: this.askText,
        price: this.askPrice,
      };
      console.log(options);
      let { data } = await http.post("/proposals", options);
      console.log(data);
      if (data == "login") {
        util.sessionLogout();
        this.$toast.error("세션이 만료되었습니다.", {
          position: "bottom-right",
          timeout: 700,
        });
      } else if (data.result == "SUCCESS") {
        this.$toast.success("문의 등록되었습니다", {
          position: "bottom-right",
          timeout: 1000,
          draggablePercent: 0.48,
        });
        this.getIsAsked();
      } else {
        this.$toast.error("문의에 실패하였습니다 !", {
          position: "bottom-right",
          timeout: 1800,
          draggablePercent: 0.48,
        });
      }
      this.askPhone = "";
      this.askText = "";
      this.askPrice = "";
    },

    async getIsAsked() {
      let { data } = await http.get(`/proposals/house?houseNo=${this.$route.params.houseNo}&state=001`);
      if (data == "login") {
        util.sessionLogout();
        this.$toast.error("세션이 만료되었습니다.", {
          position: "bottom-right",
          timeout: 700,
        });
      }
      if (data.list.length > 0) {
        this.isAsked = true;
        this.proposedId = data.list[0].proposalId;
        this.proposedPrice = data.list[0].price;
      } else {
        this.isAsked = false;
      }
    },
    async cancelAsk() {
      let { data } = await http.delete(`/proposals/${this.proposedId}`);
      if (data == "login") {
        util.sessionLogout();
        this.$toast.error("세션이 만료되었습니다.", {
          position: "bottom-right",
          timeout: 700,
        });
      }
      if (data.result == "SUCCESS") {
        this.$toast.success("문의를 취소했습니다", {
          position: "bottom-right",
          timeout: 1000,
          draggablePercent: 0.48,
        });

        this.getIsAsked();
      } else {
        this.$toast.error("문의를 취소하지 못했습니다 !", {
          position: "bottom-right",
          timeout: 1800,
          draggablePercent: 0.48,
        });
      }
    },
    async getIsBookmarked() {
      let response = await http.get("/bookmark/check/" + this.$route.params.houseNo);

      let { data } = response;
      if (data == "login") {
        util.sessionLogout();
        this.$toast.error("세션이 만료되었습니다.", {
          position: "bottom-right",
          timeout: 700,
        });
      }
      console.log(data.result);
      this.bookmarkColor = data.result;
    },
    async deleteBookmark() {
      try {
        const options = {
          houseNo: this.info.houseNo,
        };

        console.log(options);
        let response = await http.delete("/bookmark/del", {
          params: options,
        });
        let { data } = response;
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        }
        console.log(data.result);

        if (data.result === "SUCCESS") {
          this.$toast.success("관심매물이 삭제되었습니다.", {
            position: "bottom-right",
            timeout: 1200,
            draggablePercent: 0.48,
          });
        } else {
          alert("실패");
        }
      } catch (error) {
        console.error("error");
      }
    },
    async insertBookmark() {
      try {
        const options = {
          houseNo: this.info.houseNo,
        };
        let { data } = await http.post("/bookmark", options);
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        }
        if (data.result === "SUCCESS") {
          this.$toast.success("관심매물에 추가되었습니다.", {
            position: "bottom-right",
            timeout: 1200,
            draggablePercent: 0.48,
          });
        } else {
          alert("실패");
        }
      } catch (error) {
        console.error("error");
      }
    },

    bookmarking() {
      if (this.bookmarkColor == "gray") {
        this.bookmarkColor = "warn";
        this.insertBookmark();
      } else {
        this.bookmarkColor = "gray";
        this.deleteBookmark();
      }
    },
  },
  created() {
    //console.log("created!!!")
    this.getInfo();
    this.getIsAsked();
    this.getIsBookmarked();
  },
};
</script>

<style lang="scss" scoped>
.container-page {
  width: 100%;
  margin-top: 80px;
}
.index-contents {
  padding: 0 20px;
  max-width: 1280px;
  height: 100%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
}
.title-box {
  margin-top: 40px;
  height: 150px;
  display: flex;
  flex-direction: row;
}
.title-left {
  width: 70%;
  height: 100%;
}
.title-main {
  display: flex;
  flex-direction: row;
}
.title-right {
  padding-top: 20px;
  width: 30%;
  height: 100%;
  text-align: right;
}

.titleName {
  font-size: 50px;
}

.phone-text {
  margin-top: 10px;
  font-size: 20px;
}
#map {
  margin-top: 30px;
}
.main-container {
  margin-top: 40px;
  display: flex;
  flex-direction: column;
}
.info-container {
  margin: 15px 0 10px 0;
  width: 100%;
  display: flex;
  flex-direction: row;
  background-color: white !important;
}
.info-left {
  padding-left: 40px;
  width: 35%;
}
.info-right {
  width: 65%;
}
.info-table {
  width: 30%;
  height: 100%;

  display: flex;
  flex-direction: row;
}
.info-title {
  font-size: 30px;
}

.font-s1 {
  font-size: 30px;
}
.option-wrapper {
  border-radius: 10px;
  margin-top: 10px;
  display: flex;
  flex-direction: row;
}
.option-box {
  margin-right: 30px;
  display: flex;
  flex-direction: row;
  text-align: center;
}
.ask-text {
  padding: 0 15px 20px;
}
.table {
  width: 270px;
  & td {
    padding: 0 16px 0 0 !important;
  }
}
</style>
