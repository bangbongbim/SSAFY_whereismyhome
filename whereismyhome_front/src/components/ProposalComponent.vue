/*eslint-disable */
<template>
  <div class="container">
    <div class="select-box">
      <h3>나의 문의 내역</h3>
      <v-select
        v-model="selectedState"
        @change="getMyProposal()"
        class="search-select"
        :items="states"
        item-text="name"
        item-value="value"
        dense
        solo
        label="문의 상태"
      ></v-select>
    </div>

    <template>
      <v-data-table
        :headers="headers"
        :items="myProposal"
        sort-by="calories"
        class="elevation-1"
      >
        <template v-slot:top>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-dialog v-model="dialogDelete" max-width="400px">
            <v-card>
              <v-card-title class="text-h5"
                >해당 문의 내역을 삭제하시겠습니까?</v-card-title
              >
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="closeDelete"
                  >Cancel</v-btn
                >
                <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                  >OK</v-btn
                >
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </template>
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
        </template>
      </v-data-table>
    </template>
  </div>
</template>
<script>
import Vue from "vue";
import "vue-toastification/dist/index.css";
import Toast from "vue-toastification";
import http from "../common/axios";
import util from "@/common/util.js"; //util.js import

const options = {
  transition: "Vue-Toastification__bounce",
  maxToasts: 5,
  newestOnTop: true,
};
Vue.use(Toast, options);

export default {
  data() {
    return {
      myProposal: [],
      states: [
        { name: "모두보기", value: null },
        { name: "등록완료", value: "001" },
        { name: "처리완료", value: "002" },
      ],
      selectedState: null,
      delSelectedId: 0,
      dialogDelete: false,
      headers: [
        {
          text: "아파트",
          align: "start",
          value: "houseName",
        },
        { text: "문의가격(만원)", value: "price" },
        { text: "문의내용", value: "ask" },
        { text: "등록일", value: "regDt" },
        { text: "상태", value: "state" },

        { text: "삭제", value: "actions", sortable: false },
      ],
    };
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  methods: {
    async getMyProposal() {
      try {
        let { data } = await http.get(
          "/proposals/proposer?state=" + this.selectedState
        );
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        }
        if (data.result == "SUCCESS") {
          this.myProposal = data.list;
          console.log(this.myProposal);
          for (var i = 0; i < this.myProposal.length; i++) {
            this.myProposal[i].state = this.makeState(this.myProposal[i].state);
            this.myProposal[i].regDt = this.makeDateStr(
              this.myProposal[i].regDt
            );
            if (this.myProposal[i].ask.length > 32) {
              this.myProposal[i].ask =
                this.myProposal[i].ask.substring(0, 30) + "..";
            }
          }
        }
      } catch (error) {
        this.$toast.error("로그인 상태를 확인하세요 !", {
          position: "bottom-right",
          timeout: 1800,
          draggablePercent: 0.48,
        });
        console.error(error);
      }
    },
    async deleteProposal(proposalId) {
      let $this = this;
      try {
        let { data } = await http.delete("/proposals/" + proposalId);
        if (data == "login") {
          util.sessionLogout();
          this.$toast.error("세션이 만료되었습니다.", {
            position: "bottom-right",
            timeout: 700,
          });
        }
        if (data.result == "SUCCESS") {
          console.log(data);
          $this.getMyProposal();
        }
      } catch (error) {
        this.$toast.error("이미 삭제된 문의 입니다 !", {
          position: "bottom-right",
          timeout: 1800,
          draggablePercent: 0.48,
        });
        console.error(error);
      }
    },
    makeDateStr(regDt) {
      return util.makeDateStr(
        regDt.date.year,
        regDt.date.month,
        regDt.date.day,
        "-"
      );
    },
    makeTimeStr(regDt) {
      return util.makeTimeStr(
        regDt.time.hour,
        regDt.time.minute,
        regDt.time.second,
        ":"
      );
    },

    deleteItem(item) {
      console.log("delte ", item);
      this.dialogDelete = true;
      this.delSelectedId = item.proposalId;
    },

    deleteItemConfirm() {
      this.deleteProposal(this.delSelectedId);
      this.closeDelete();
    },

    closeDelete() {
      this.dialogDelete = false;
      this.delSelectedId = 0;
    },
    makeState(state) {
      if (state === "001") return "등록완료";
      else if (state === "002") return "처리완료";
    },
  },
  mounted() {
    window.scrollTo(0, 0);
  },
  created() {
    this.getMyProposal();
  },
};
</script>

<style scoped>
.search-select {
  width: 300px;
  height: 55px;
  border: none;
  background-color: #ffff;
  text-align: center;
  box-shadow: rgb(0 0 0 / 10%) 0px 2px 2px;
  border-radius: 5px;
}

.search-select:first-child {
  margin-right: 1rem;
}
</style>
