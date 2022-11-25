<template>
  <div>
    <header-component></header-component>
    <detail-board-component></detail-board-component>
    <footer-component></footer-component>
  </div>
</template>

<script>
import HeaderComponent from "../components/common/HeaderComponent.vue";
import FooterComponent from "../components/common/FooterComponent.vue";
import DetailBoardComponent from "../components/board/DetailBoardComponent.vue";

export default {
  components: { HeaderComponent, FooterComponent, DetailBoardComponent },
};
</script>

<style></style>
