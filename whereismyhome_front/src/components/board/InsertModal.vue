<template>
  <div>
    <div class="modal fade" id="insertModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">글 쓰기</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="titleInsert" class="form-label">제목</label>
              <input type="text" v-model="title" class="form-control" id="titleInsert" />
            </div>
            <div class="mb-3">
              <label for="contentInsert" class="form-label">내용</label>
              <!-- CKEditor insert -->
              <div id="divEditorInsert"></div>
            </div>
            <div class="mb-3">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="chkFileUploadInsert" v-model="attatchfile" />
                <label class="form-check-label" for="chkFileUploadInsert">파일 추가</label>
              </div>
            </div>
            <div class="mb-3" v-show="attatchfile" id="imgFileUploadInsertWrapper">
              <input @change="changeFile" type="file" id="inputFileUploadInsert" multiple />
              <div id="imgFileUploadInsertThumbnail" class="thumbnail-wrapper">
                <!-- vue 방식으로 첨부파일 이미지 thumbnail을 보이는 방법 -->
                <img v-for="(file, index) in fileList" :key="index" v-bind:src="file" alt="" />
              </div>
            </div>
            <button id="btnBoardInsert" type="button" class="btn btn-sm btn-primary float-end" @click="boardInsert">등록</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import CKEditor from "@ckeditor/ckeditor5-vue2";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import VueAlertify from "vue-alertify";
import http from "@/common/axios";
import util from "@/common/util.js"; //util.js import

Vue.use(CKEditor).use(VueAlertify);

export default {
  data() {
    return {
      CKEditor: null,
      attatchfile: false,
      fileList: [],
      title: "",
    };
  },
  methods: {
    changeFile(fileEvent) {
      // 파일 미리보기
      // 첨부할 파일목록
      this.fileList = [];

      const fileArray = Array.from(fileEvent.target.files);

      fileArray.forEach((file) => {
        this.fileList.push(URL.createObjectURL(file));
      });
    },
    initUI() {
      this.title = "";
      this.CKEditor.setData("");
      this.attatchfile = false;
      this.fileList = [];
      document.querySelector("#inputFileUploadInsert").value = "";
    },
    async boardInsert() {
      let formData = new FormData();
      formData.append("title", this.title);
      formData.append("content", this.CKEditor.getData());

      // file 각각을 formData에 추가
      let attatchFiles = document.querySelector("#inputFileUploadInsert").files;
      if (attatchFiles.length > 0) {
        const fileArray = Array.from(attatchFiles);
        fileArray.forEach((file) => formData.append("file", file));
      }

      // multipart/form-data
      let options = {
        headers: { "Content-type": "multipart/form-data" },
      };

      try {
        let response = await http.post("/boards", formData, options);
        let { data } = response;

        console.log(data);
        if (data.reslt == "login") {
          //세션 타임아웃
          util.sessionLogout();
        } else if (data.reslt == "noAuthorized") {
          this.$toast.error("권한이 없습니다.", {
            position: "bottom-right",
            timeout: 1800,
            draggablePercent: 0.48,
          });
        } else {
          // 등록성공
          this.$toast.success("글이 등록되었습니다.", {
            position: "bottom-right",
            timeout: 1000,
            draggablePercent: 0.48,
          });
          this.closeModal();
        }
      } catch (error) {
        this.$toast.error("글 등록과정에서 오류가 발생했습니다.", {
          position: "bottom-right",
          timeout: 1800,
          draggablePercent: 0.48,
        });
        console.error(error);
      }
    },
    closeModal() {
      this.fileList = [];
      this.$emit("call-parent-insert");
    },
  },
  async mounted() {
    try {
      this.CKEditor = await ClassicEditor.create(document.querySelector("#divEditorInsert"));
    } catch (error) {
      console.error(error);
    }
    // bootstrap event 처리자 등록
    // show.bs.modal <= 해당 모당창이 show 될 때 호출
    let $this = this; // $this <= vue component
    this.$el.addEventListener("show.bs.modal", function () {
      // 현 시점의 this는 event 처리자, vue component가 아니다.
      $this.initUI();
    });
  },
};
</script>

<style>
/* deep select */
.ck-editor__editable[role="textbox"] {
  height: 200px;
}

.thumbnail-wrapper {
  margin-top: 5px;
}

.thumbnail-wrapper img {
  width: 100px !important;
  margin-right: 5px;
  max-width: 100%;
}
</style>
