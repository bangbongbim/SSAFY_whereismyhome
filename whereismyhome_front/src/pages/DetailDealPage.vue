<template>
  <div>
    <header-component></header-component>
    <detail-deal-component></detail-deal-component>
    <footer-component></footer-component>
  </div>
</template>

<script>
import HeaderComponent from "../components/common/HeaderComponent.vue";
import FooterComponent from "../components/common/FooterComponent.vue";
import DetailDealComponent from "../components/DetailDealComponent.vue";

export default {
  components: { HeaderComponent, FooterComponent, DetailDealComponent },
};
</script>

<style></style>
