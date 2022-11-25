import Vue from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";

import router from "./routes/router.js";
import store from "@/store/store.js";
Vue.config.productionTip = false;

//bootstrap 5
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

new Vue({
  vuetify,
  router,
  render: (h) => h(App),
  store,
}).$mount("#app");
