function getContextPath() {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  return location.href.substring(hostIndex, location.href.indexOf("/", hostIndex + 1));
}

let contextPath=getContextPath();

window.onload = function () {
  getInfo();
  document.querySelector("#btnLogout").onclick = function () {
    logout();
  };
  document.querySelector("#userPassword").focus(); //커서가 들어가있도록
  document.querySelector("#btnChange").onclick = function () {
    if (document.querySelectorAll("form .is-invalid").length > 0) {
      //form에 invalid가 있으면
      alert("입력이 올바르지 않습니다.");
    } else {
      change();
    }
  };

  document.querySelector("#btnUnregist").onclick = function () {
    if (document.querySelectorAll("form .is-invalid").length > 0) {
      alert("입력이 올바르지 않습니다.");
    } else {
      unregist();
    }
  };
};

async function logout() {
  let response = await fetch(contextPath+"/user/logout");
  let data = await response.json(); //==Json.parse();

  if (data.result == "success") {
    window.location.href = "./index.jsp";
  } else if (data.result == "fail") {
    alert("로그아웃 실패");
    window.location.href = "./index.jsp";
  }
}

async function getInfo() {
  let response = await fetch(contextPath+"/user/getInfo");
  let data = await response.json(); //==Json.parse();

  if (data.result == "success") {
    console.log("로그인 상태");
    //내정보에 정보 넣기
    makeInfo(data);
  } else if (data.result == "fail") {
    console.log("비로그인 상태");
    window.location.href = "./index.jsp";
    //버튼숨기기
  }
}
async function change() {
  let userPassword = document.querySelector("#userPassword").value;
  let userName = document.querySelector("#userName").value;

  let urlParmas = new URLSearchParams({
    userPassword: userPassword,
    userName: userName,
  });

  let fetchOptions = {
    method: "POST",
    body: urlParmas,
  };

  let response = await fetch(contextPath+"/user/change", fetchOptions); //fetch( url(login servlet), option );
  let data = await response.json(); //==Json.parse();
  console.log(data.result);
  if (data.result == "success") {
    //alertify.alert("Welcom", "회원가입을 축하합니다. 로그인 페이지로 이동합니다.", function () {});
    alert("수정완료.");
    window.location.href = "./index.jsp";
    //login.jsp => board.jsp로 페이지 이동
  } else if (data.result == "fail") {
    alert("서버오류");
  } else if (data.result == "unlogin") {
    alert("로그인 후 수정이 가능합니다.");
  }
}

async function unregist() {
  let userPassword = document.querySelector("#userPassword").value;
  let userName = document.querySelector("#userName").value;

  let urlParmas = new URLSearchParams({
    userPassword: userPassword,
    userName: userName,
  });

  let fetchOptions = {
    method: "POST",
    body: urlParmas,
  };

  let response = await fetch(contextPath+"/user/unregist", fetchOptions); //fetch( url(login servlet), option );
  let data = await response.json(); //==Json.parse();
  console.log(data.result);
  if (data.result == "success") {
    //alertify.alert("Welcom", "회원가입을 축하합니다. 로그인 페이지로 이동합니다.", function () {});
    alert("삭제완료.");
    window.location.href = "./index.jsp";
    //login.jsp => board.jsp로 페이지 이동
  } else if (data.result == "fail") {
    alert("서버오류");
  } else if (data.result == "unlogin") {
    alert("로그인 후 수정이 가능합니다.");
    window.location.href = "./index.jsp";
  } else if (data.result == "mismatch") {
    alert("패스워드와 이름이 다릅니다.");
  }
}
function makeInfo(data) {
  let myEmail = data.userEmail;
  console.log(myEmail);
  document.querySelector("#userEmail").innerText = myEmail;
  let myName = data.userName;
  document.querySelector("#userName").value = myName;
}

function validate() {
  // return true / false
  let loginPasswordValid = false;

  let loginPassword = document.querySelector("#loginPassword").value;
  if (loginPassword.length > 0) {
    loginPasswordValid = true;
  }

  return loginPasswordValid;
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

  if (patternEngAtListOne.test(userPassword) && patternSpeAtListOne.test(userPassword) && patternNumAtListOne.test(userPassword) && userPassword.length >= 8) {
    return true;
  } else return false;
}

function validatePassword2(userPassword2) {
  //pw2 유효성
  if (userPassword2 == document.querySelector("#userPassword").value) return true;
  else return false;
}

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
