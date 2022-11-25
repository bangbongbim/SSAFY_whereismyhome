<template>
  <div>
    <header-component></header-component>
    <login-component></login-component>
  </div>
</template>

<script>
import HeaderComponent from "../components/common/HeaderComponent.vue";
import LoginComponent from "../components/LoginComponent.vue";
export default {
  components: { HeaderComponent, LoginComponent },
};
</script>

<style>

</style>
