<template>
  <div>
    <header-component></header-component>
    <modify-user-info-component></modify-user-info-component>
  </div>
</template>

<script>
import HeaderComponent from "@/components/common/HeaderComponent.vue";
import ModifyUserInfoComponent from "@/components/ModifyUserInfoComponent.vue";
export default {
  components: {
    HeaderComponent,
    ModifyUserInfoComponent,
  },
};
</script>

<style>

</style>
