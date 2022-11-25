<template>
  <div>
    <header-component></header-component>
    <div class="body-container">
      <deal-search-component></deal-search-component>
      <div class="main-container">
        <deal-info-component></deal-info-component>
        <deal-map-component></deal-map-component>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderComponent from "../components/common/HeaderComponent.vue";
import DealSearchComponent from "../components/DealSearchComponent.vue";
import DealInfoComponent from "../components/DealInfoComponent.vue";
import DealMapComponent from "../components/DealMapComponent.vue";

export default {
  components: { HeaderComponent, DealInfoComponent, DealMapComponent, DealSearchComponent },
};
</script>

<style scoped>
.body-container {
  margin-top: 80px;
  width: 100%;
  min-height: 90vh;
  height: 100%;
}

.main-container {
  display: flex;
  height: calc(100vh - 80px);
}
</style>
