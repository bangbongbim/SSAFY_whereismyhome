<template>
  <div>
    <header-component></header-component>
    <board-main></board-main>
    <footer-component></footer-component>
  </div>
</template>

<script>
import HeaderComponent from "../components/common/HeaderComponent.vue";
import FooterComponent from "../components/common/FooterComponent.vue";
import BoardMain from "../components/board/BoardMain.vue";

export default {
  components: { HeaderComponent, FooterComponent, BoardMain },
};
</script>

<style></style>
