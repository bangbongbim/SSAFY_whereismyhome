import Vue from "vue";
import Vuetify from "vuetify/lib/framework";

Vue.use(Vuetify);

const opts = {};

export default new Vuetify({
  theme: {
    themes: {
      light: {
        LightCobaltBlue: "#99a9e8",
        PowderBlue: "#acdFe6",
        Beige: "#f5f0da",
        MediumLavendaerMagenta: "#dda5e8",
        MiddleBluePurple: "#8f73bf",
        primary: "#326789",
        second: "#78A6C8",
        background: "#E9EEF4",
        warn: "#E65C4F",
      },
    },
    opts,
  },
});
