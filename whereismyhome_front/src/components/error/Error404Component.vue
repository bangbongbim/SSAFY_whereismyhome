<template>
  <div class="main">
    <div class="background-box"></div>
    <div class="text-box">
      <h1>찾을수 없는 페이지 입니다.</h1>
      <br />
      <h2>요청하신 페이지가 사라졌거나, 잘못된 경로를 이용하셨어요 : )</h2>
    </div>
    <div class="btn-box">
      <v-btn class="btn" to="/" elevation="6" x-large>홈으로 가기</v-btn>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped lang="scss">
.main {
  max-width: 1280px;
  height: 1000px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
}
.background-box {
  background-image: url("../../assets/img/404.jpg");
  widows: 100%;

  height: 500px;
  background-position: center;
  background-repeat: no-repeat;
}
.text-box {
  display: flex;
  flex-direction: column;
  text-align: center;
}
.btn-box {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  .btn {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    width: 120px;
    padding: 10px;
  }
}
</style>
