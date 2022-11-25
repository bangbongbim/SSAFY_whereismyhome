export default {
  state: {
    selectedSigugun: undefined,
    selectedDong: undefined,
    searchedDeals: [],
    lastClickedLng:undefined,
    lastClickedLat:undefined,
  },
  mutations: {
    SET_SELECTEDSIGUGUN(state, selectedSigugun) {
      state.selectedSigugun = selectedSigugun;
    },
    SET_SELECTEDDONG(state, selectedDong) {
      state.selectedDong = selectedDong;
    },
  },
  actions: {
    setSelectedDong(context, selectedDong) {
      context.commit("SET_SELECTEDDONG", selectedDong);
    },
  },
  getters: {},
};
