function getContextPath() {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  return location.href.substring(hostIndex, location.href.indexOf("/", hostIndex + 1));
}

let contextPath=getContextPath();

window.onload = function () {
  //getInfo();
  document.querySelector("#userEmail").focus(); //커서가 들어가있도록

  //유효성 검사 처리
  document.querySelector("#userName").onblur = function () {
    //커서가 떠났을 때?
    if (validateUserName(this.value)) {
      //유효할때
      this.classList.remove("is-invalid"); // userName의 클래스에 is-invalid없애기
      this.classList.add("is-valid");
    } else {
      this.classList.add("is-invalid");
      this.classList.remove("is-valid");
    }
  };
  document.querySelector("#userPassword").onblur = function () {
    if (validatePassword(this.value)) {
      //유효할때
      this.classList.remove("is-invalid"); // 클래스에 is-invalid없애기
      this.classList.add("is-valid");
    } else {
      this.classList.add("is-invalid");
      this.classList.remove("is-valid");
    }
  };

  document.querySelector("#userPassword2").onblur = function () {
    if (validatePassword2(this.value)) {
      //유효할때
      this.classList.remove("is-invalid"); //클래스에 is-invalid없애기
      this.classList.add("is-valid");
    } else {
      this.classList.add("is-invalid");
      this.classList.remove("is-valid");
    }
  };
  document.querySelector("#userEmail").onblur = function () {
    if (validateEmail(this.value)) {
      //유효할때
      this.classList.remove("is-invalid"); //클래스에 is-invalid없애기
      this.classList.add("is-valid");
    } else {
      this.classList.add("is-invalid");
      this.classList.remove("is-valid");
    }
  };

  document.querySelector("#btnRegist").onclick = function () {
    if (document.querySelectorAll("form .is-invalid").length > 0) {
      //form에 invalid가 있으면
      alert("입력이 올바르지 않습니다.");
    } else {
      console.log("asdf");
      regist();
    }
  };

  async function getInfo() {
    let response = await fetch(contextPath+"/user/getInfo");
    let data = await response.json(); //==Json.parse();

    if (data.result == "success") {
      console.log("로그인 상태");
      //내정보에 정보 넣기
      makeInfo(data);
      //버튼 보이기
      document.querySelector("#btnNavInfo").setAttribute("style", "display:block");
      document.querySelector("#btnNavSignup").setAttribute("style", "display:none");
      document.querySelector("#btnNavLogin").setAttribute("style", "display:none");
      document.querySelector("#btnNavLogout").setAttribute("style", "display:block");
    } else if (data.result == "fail") {
      console.log("비로그인 상태");
      //버튼숨기기
      document.querySelector("#btnNavInfo").setAttribute("style", "display:none");
      document.querySelector("#btnNavSignup").setAttribute("style", "display:block");
      document.querySelector("#btnNavLogin").setAttribute("style", "display:block");
      document.querySelector("#btnNavLogout").setAttribute("style", "display:none");
    }
  }

  function validateUserName(userName) {
    //name 유효성
    if (userName.length >= 4) return true;
    else return false;
  }

  function validatePassword(userPassword) {
    //pw 유효성
    var patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
    var patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
    var patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

    if (
      patternEngAtListOne.test(userPassword) &&
      patternSpeAtListOne.test(userPassword) &&
      patternNumAtListOne.test(userPassword) &&
      userPassword.length >= 8
    ) {
      return true;
    } else return false;
  }

  function validatePassword2(userPassword2) {
    //pw2 유효성
    if (userPassword2 == document.querySelector("#userPassword").value) return true;
    else return false;
  }

  function validateEmail(userEmail) {
    //email 유효성
    // ^ 시작일치, $ 끝 일치
    // {2, 3} 2개 ~ 3개
    // * 0회 이상, + 1회 이상
    // [-.] - 또는 _ 또는 .
    // ? 없거나 1회
    let regexp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if (regexp.test(userEmail)) return true;
    else return false;
  }

  async function regist() {
    let userEmail = document.querySelector("#userEmail").value;
    let userPassword = document.querySelector("#userPassword").value;
    let userName = document.querySelector("#userName").value;
    let userClst = document.querySelector(".radios:checked").value;
    console.log(userClst);
    
    
    let urlParmas = new URLSearchParams({
      userEmail: userEmail,
      userPassword: userPassword,
      userName: userName,
      userClst: userClst,
    });

    let fetchOptions = {
      method: "POST",
      body: urlParmas,
    };

    let response = await fetch(contextPath+"/user/regist", fetchOptions); //fetch( url(login servlet), option );
    let data = await response.json(); //==Json.parse();
    console.log(data.result);
    if (data.result == "success") {
      //alertify.alert("Welcom", "회원가입을 축하합니다. 로그인 페이지로 이동합니다.", function () {});
      alert("환영합니다.");
      window.location.href = "../jsp/index.jsp";
      //login.jsp => board.jsp로 페이지 이동
    } else if (data.result == "fail") {
      alert("서버오류");
    } else if (data.result == "duplicate") {
      alert("중복 아이디");
    }
  }
};
