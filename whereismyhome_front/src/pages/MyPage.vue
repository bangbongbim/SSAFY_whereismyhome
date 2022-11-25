<template>
  <div>
    <header-component></header-component>
    <div class="container">
      <div class="contents">
        <div class="tab-box">
          <div class="tab-header-box">
            <v-tabs v-model="tab" centered fixed-tabs>
              <v-tabs-slider color="warn"></v-tabs-slider>
              <v-tab class="tab-title"> 회원정보수정 </v-tab>
              <v-tab class="tab-title"> 문의내역</v-tab>
              <v-tab class="tab-title"> 관심매물관리 </v-tab>
            </v-tabs>
          </div>

          <v-tabs-items v-model="tab">
            <v-tab-item>
              <update-user-component></update-user-component>
            </v-tab-item>
            <v-tab-item>
              <proposal-component></proposal-component>
            </v-tab-item>
            <v-tab-item>
              <bookmark-manage-component></bookmark-manage-component>
            </v-tab-item>
          </v-tabs-items>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderComponent from "@/components/common/HeaderComponent.vue";
import UpdateUserComponent from "@/components/UpdateUserComponent.vue";
import ProposalComponent from "@/components/ProposalComponent.vue";
import BookmarkManageComponent from "@/components/BoookmarkManageComponent.vue";
export default {
  components: {
    HeaderComponent,
    UpdateUserComponent,
    ProposalComponent,
    BookmarkManageComponent,
  },
  data() {
    return {
      tab: null,
    };
  },
  methods: {
    tabHandler() {},
  },
  watch: {
    tab: function () {
      console.log(this.tab);
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
};
</script>

<style lang="scss" scoped>
.container {
  margin-top: 80px;
  width: 100%;
  min-height: 100vh;
  .contents {
    display: flex;
    justify-content: center;
    align-items: center;
    max-width: 1280px;
    margin: 0 auto;
    height: 100%;
    .tab-box {
      width: 100%;
      .tab-header-box {
        margin-top: 20px;
      }

      .tab-title {
        font-size: 1rem;
        letter-spacing: 5px;
        color: black;
      }
      .v-tab--active {
        color: $warn;
      }
    }
  }
}
</style>
