<template>
  <v-app>
    <transition name="fade">
      <router-view></router-view>
    </transition>
  </v-app>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<style>
.fate-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fate-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
