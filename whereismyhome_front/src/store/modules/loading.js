export default {
  state: {
    isLoading: false,
  },
  motations: {
    SET_ISLOGIN(state, payload) {
      state.isLoading = payload.isLoading;
    },
  },
  actions: {
    setIsLoading(context, state) {
      context.commit("SET_ISLOGIN", state);
    },
  },
  getters: {
    isLoading: function (state) {
      return state.isLoading;
    },
  },
};
