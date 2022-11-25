export default {
  state: {
    isLogin: false,
    userName: "",
    userEmail: "",
    userProfileImageUrl: "",
    userClsf: "",
  },
  mutations: {
    SET_LOGIN(state, payload) {
      state.isLogin = payload.isLogin;
      state.userName = payload.userName;
      state.userProfileImageUrl = payload.userProfileImageUrl;
      state.userEmail = payload.userEmail;
      state.userClsf = payload.userClsf;
    },

    RESET_STATE(state) {
      state.isLogin = false;
      state.userName = "";
      state.userEmail = "";
      state.userProfileImageUrl = "";
      state.userClsf = "";
    },
  },
  actions: {
    setLogin(context, dto) {
      context.commit("SET_LOGIN", dto);
    },
    resetState(context) {
      context.commit("RESET_STATE");
    },
  },

  getters: {
    isLogin: function (state) {
      return state.isLogin;
    },
  },
};
