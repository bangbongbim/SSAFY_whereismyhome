<template>
  <div>
    <header-component></header-component>
    <register-component></register-component>
  </div>
</template>

<script>
import HeaderComponent from "@/components/common/HeaderComponent.vue";
import RegisterComponent from "@/components/RegisterComponent.vue";
export default {
  components: { HeaderComponent, RegisterComponent },
};
</script>

<style>

</style>
