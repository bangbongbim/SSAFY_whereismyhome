<template>
  <!--<button @click="panTo(31,122)"></button>-->
  <div id="map"></div>
</template>

<script>
import { BUS } from "@/bus/EventBus.js";

export default {
  data() {
    return {
      map: {},
      markers: [],
    };
  },
  mounted() {
    setTimeout(() => {
      if (window.kakao && window.kakao.maps) {
        this.initMap();
      } else {
        console.log("else");
        const script = document.createElement("script");
        /* global kakao */
        script.onload = () => kakao.maps.load(this.initMap);
        //script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=ac9eedff848a7c184a6a6818246bf6a5&libraries=services";
        script.src =
          "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" +
          process.env.VUE_APP_KAKAO_KEY;
        document.head.appendChild(script);
      }
    }, 1000);

    var $this = this;
    BUS.$on("mouseOver", function (info, ind) {
      //info item에 마우스 올라감
      //console.log('mouseOver', info);
      $this.panTo(info.lat, info.lng);
      let marker = $this.markers[ind];

      var markerImage = new kakao.maps.MarkerImage(
        require("../assets/img/pick3.png"),
        new kakao.maps.Size(44, 70)
      );
      marker.setImage(markerImage);
      marker.setZIndex(3);
    });
    BUS.$on("mouseLeave", function (info, ind) {
      //info item에 마우스 빠짐
      //console.log('mouseOver', info);
      let marker = $this.markers[ind];

      var markerImage = new kakao.maps.MarkerImage(
        "http://t1.daumcdn.net/mapjsapi/images/marker.png",
        new kakao.maps.Size(29, 42)
      );
      marker.setImage(markerImage);
      marker.setZIndex(0);
    });
  },
  methods: {
    initMap() {
      console.log("init!!!!");
      //console.log(process.env.VUE_APP_KAKAO_KEY);
      const container = document.getElementById("map");
      if (this.$store.state.deal.lastClickedLat != undefined) {
        let lat = this.$store.state.deal.lastClickedLat;
        let lng = this.$store.state.deal.lastClickedLng;
        const options = {
          center: new kakao.maps.LatLng(lat, lng),
          level: 5,
        };
        this.map = new kakao.maps.Map(container, options);
        this.setMakersAndEvent();
      } else {
        const options = {
          center: new kakao.maps.LatLng(33.450701, 126.570667),
          level: 5,
        };
        this.map = new kakao.maps.Map(container, options);
      }
    },
    panTo(lat, lng) {
      var moveLatLon = new kakao.maps.LatLng(lat, lng);
      // 지도 중심을 부드럽게 이동시킵니다
      // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      this.map.panTo(moveLatLon);
    },

    setMakersAndEvent() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }

      this.markers = [];
      for (i = 0; i < this.$store.state.deal.searchedDeals.length; i++) {
        let lat = this.$store.state.deal.searchedDeals[i].lat;
        let lng = this.$store.state.deal.searchedDeals[i].lng;
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: new kakao.maps.LatLng(lat, lng),
        });
        this.markers.push(marker);
        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: `<div class ="info-window" id=${i}>${this.$store.state.deal.searchedDeals[i].aptName}</div>`,
        });

        // 마커에 이벤트를 등록하는 함수 만들고 즉시 호출하여 클로저를 만듭니다
        // 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        var $this = this;
        (function (marker, infowindow) {
          // 마커에 mouseover 이벤트를 등록하고 마우스 오버 시 인포윈도우를 표시합니다
          kakao.maps.event.addListener(marker, "mouseover", function () {
            //console.log("over!!!!")
            infowindow.open($this.map, marker);
          });
          kakao.maps.event.addListener(marker, "click", function () {
            //console.log("click!!!!")
            console.log(infowindow.Uf.lastChild.id);
            let clickedId = infowindow.Uf.lastChild.id;
            $this.clickedInfoHightlight(clickedId);
            $this.moveScroll(clickedId);
          });
          // 마커에 mouseout 이벤트를 등록하고 마우스 아웃 시 인포윈도우를 닫습니다
          kakao.maps.event.addListener(marker, "mouseout", function () {
            infowindow.close();
          });
        })(marker, infowindow);
      }
    },
    clickedInfoHightlight(clickedId) {
      let box = document.querySelectorAll(".info-box")[clickedId];
      //console.log(box);
      box.style = `background-color: #e9eef4`;
      setTimeout(() => {
        box.style.removeProperty("background-color");
      }, 2000);
    },
    get_scroll_height(clickedId) {
      return document.querySelector(".item-box").offsetHeight * clickedId;
    },

    moveScroll(clickedId) {
      document.querySelector(".info-wrapper").scrollTop =
        this.get_scroll_height(clickedId);
    },
  },
  computed: {
    check_searchedDeals() {
      return this.$store.state.deal.searchedDeals;
    },
  },
  watch: {
    check_searchedDeals() {
      //searchedDeals가 변경되면
      if (this.$store.state.deal.searchedDeals.length == 0) {
        this.panTo(33.450701, 126.570667);
      } else {
        let lat = this.$store.state.deal.searchedDeals[0].lat;
        let lng = this.$store.state.deal.searchedDeals[0].lng;
        this.panTo(lat, lng);
        this.setMakersAndEvent();
      }
    },
  },
};
</script>

<style scoped>
#map {
  width: calc(100% - 680px);
  height: calc(100vh - 80px);
  overflow: hidden;
}
.info-window {
  width: 30px;
}
</style>
