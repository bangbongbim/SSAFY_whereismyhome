<template>
  <footer>
    <div class="footer-contents">
      <div class="company-info-box">
        <p class="company-title">구해줘 홈즈</p>
        <p>주소 : 부산광역시 강서구 녹산산업중로 333</p>
        <span>TEL : 051-888-7878</span>
        <span>FAX : 051-888-7118</span>
        <span>MAIL : parkssafy@ssafy.com</span>
      </div>
      <div class="policy-box">
        <span> 이용약관</span>
        <span>개인정보처리방침</span>
        <span>이메일무단수집거부</span>
        <span>오시는길</span>
      </div>
      <p class="copyright">ⓒ SaveMyHomes Co. Ltd. All rights reserved.</p>
    </div>
  </footer>
</template>

<script>
export default {};
</script>

<style scoped lang="scss">
footer {
  background-color: rgb(255, 255, 255);
  width: 100%;
  min-height: 200px;
  background-color: $background;

  @media (max-width: $mobile) {
    & {
      display: none;
    }
  }

  .footer-contents {
    box-sizing: border-box;
    max-width: 1280px;
    width: 100%;
    margin: auto;
    padding: 30px;
    height: 100%;
    display: flex;
    flex-direction: column;

    .company-info-box {
      margin-bottom: 20px;
      .company-title {
        font-size: 1.5rem;
        letter-spacing: 3px;
      }
      span {
        font-size: 1rem;
        color: rgb(0, 0, 0);
        &::after {
          margin: 0 10px;
          content: "  |  ";
        }
        &:last-child::after {
          content: "";
        }
      }
    }
    .policy-box {
      & span {
        font-size: 1.3rem;
        cursor: pointer;
        margin-right: 20px;
      }
    }

    .copyright {
      color: #707070;
      margin-top: 25px;
      font-size: 14px;
    }
  }
}
</style>
