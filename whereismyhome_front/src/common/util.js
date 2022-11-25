import store from "../store/store";
import router from "../routes/router.js";

export default {
  makeDateStr(year, month, day, type) {
    // type + / or . (ex)2022/7/24
    return year + type + (month < 10 ? "0" + month : month) + type + (day < 10 ? "0" + day : day);
  },
  makeTimeStr(hour, minute, second, type) {
    // type + / or . (ex)2022/7/24
    return hour + type + (minute < 10 ? "0" + minute : minute) + type + (second < 10 ? "0" + second : second);
  },
  sessionLogout() {
    console.log("sessionLogout");
    store.dispatch("resetState");

    sessionStorage.removeItem("userInfo");

    setTimeout(() => {
      router.push("/login");
    }, 700);
  },
};
