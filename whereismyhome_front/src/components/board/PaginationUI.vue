<template>
<!-- a tag href="#" 제외!!
route url 이 board 에서 / 로 변경되는 문제 발생 -->
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
      <li v-if="prev" class="page-item">
        <a class="page-link" aria-label="Previous" @click="paginationChanged(startPageIndex - 1)">
          <span aria-hidden="true">«</span>
        </a>
      </li>
      <li v-for="index in ( endPageIndex-startPageIndex + 1 )" :key="index"
          v-bind:class="{active: (startPageIndex + index - 1 == currentPageIndex)}" class="page-item">
        <a @click="paginationChanged(startPageIndex + index - 1)" 
           class="page-link">{{ startPageIndex + index - 1 }}</a> <!-- href 는 그대로, 커서 모양 유지-->
      </li>
      <li v-if="next" class="page-item">
        <a class="page-link" aria-label="Next" @click="paginationChanged(endPageIndex + 1)">
          <span aria-hidden="true">»</span>
        </a>
      </li>
    </ul>
  </nav>
</template>


<script>
export default {
  props: ['listRowCount', 'pageLinkCount', 'currentPageIndex', 'totalListItemCount'],
  computed: {
    pageCount: function(){
      return Math.ceil(this.totalListItemCount/this.listRowCount);
    },
    startPageIndex: function(){
      if( (this.currentPageIndex % this.pageLinkCount) == 0 ){ //10, 20...맨마지막
        return ((this.currentPageIndex / this.pageLinkCount)-1)*this.pageLinkCount + 1
      }else{
        return  Math.floor(this.currentPageIndex / this.pageLinkCount)*this.pageLinkCount + 1
      }
    },
    endPageIndex: function(){
      // 아래 코드는 Error in render: "RangeError: Maximum call stack size exceeded" 발생, this.endPageIndex 계산 할 때, 자기 자신 값을 단순 비교만하더라도 에러 발생 
      // if( this.endPageIndex < this.pageCount ) return this.pageCount;

      let ret = 0;
      if( (this.currentPageIndex % this.pageLinkCount) == 0 ){ //10, 20...맨마지막
        ret = ((this.currentPageIndex / this.pageLinkCount)-1)*this.pageLinkCount + this.pageLinkCount;
      }else{
        ret = Math.floor(this.currentPageIndex / this.pageLinkCount)*this.pageLinkCount + this.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ( ret > this.pageCount ) ? this.pageCount : ret;
    },
    prev: function(){
      if( this.currentPageIndex <= this.pageLinkCount ){
        return false;
      }else{
        return true;
      }
    },
    next: function(){
      if( ( Math.floor( Math.ceil(this.totalListItemCount/this.listRowCount) / this.pageLinkCount ) * this.pageLinkCount ) < this.currentPageIndex){
        return false;
      }else{
        return true;
      }
    }
  },
  methods:{
    // 부모에게 event 전달
    paginationChanged(pageIndex){
      console.log("paginationVue : paginationChanged : pageIndex : " + pageIndex );
      this.$emit('call-parent-move-page', pageIndex);
    }
  },
}
</script>