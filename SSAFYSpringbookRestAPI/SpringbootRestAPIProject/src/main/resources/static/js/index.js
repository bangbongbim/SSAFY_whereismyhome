function getContextPath() {
  var hostIndex = location.href.indexOf(location.host) + location.host.length;
  return location.href.substring(hostIndex, location.href.indexOf("/", hostIndex + 1));
}

let contextPath = getContextPath();

window.onload = function () {
  getInfo();
  getEvent();
  document.querySelector("#btnLogin").onclick = function () {
    let loginEmail = document.querySelector("#loginEmail").value;
    let loginPassword = document.querySelector("#loginPassword").value;

    // 유효성 검사 => post 전송
    if (validate()) {
      login();
    } else {
      alertify.error("아이디나 비밀번호를 확인하세요.");
    }
  };

  document.querySelector("#btnLogout").onclick = function () {
    logout();
  };
};

function validate() {
  // return true / false
  let loginEmailValid = false;
  let loginPasswordValid = false;

  let loginEmail = document.querySelector("#loginEmail").value;
  if (loginEmail.length > 0) {
    loginEmailValid = true;
  }
  let loginPassword = document.querySelector("#loginPassword").value;
  if (loginPassword.length > 0) {
    loginPasswordValid = true;
  }

  if (loginEmailValid && loginPasswordValid) {
    return true;
  }
  return false;
}

async function login() {
  //  validate()가 true를 return하면 수행
  //백엔드로 전송할 아이디 준비
  //비동기 요청
  //post
  //로그인 성공 -> 게시판 메인 페이지로 이동(board/boardMain)
  //로그인 실패 -> alert

  let loginEmail = document.querySelector("#loginEmail").value;
  let loginPassword = document.querySelector("#loginPassword").value;
  let urlParmas = new URLSearchParams({
    loginEmail: loginEmail,
    loginPassword: loginPassword,
  });

  let fetchOptions = {
    method: "POST",
    body: urlParmas,
  };

  let response = await fetch(contextPath + "/user/login", fetchOptions);
  let data = await response.json(); //==Json.parse();
  if (data.result == "success") {
    //window.location.href = "./index.jsp";
  } else if (data.result == "fail") {
    alert("아이디나 비밀번호를 확인하세요.");
  }
}

async function logout() {
  let response = await fetch(contextPath + "/user/logout");
  let data = await response.json(); //==Json.parse();

  if (data.result == "success") {
    //window.location.href = "./index.jsp";
  } else if (data.result == "fail") {
    alert("로그아웃 실패");
  }
}

async function getInfo() {
  let response = await fetch(contextPath + "/user/getInfo");
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

function makeInfo(data) {
  let myEmail = data.userEmail;
  document.querySelector("#myEmail").innerText = myEmail;
  let myName = data.userName;
  document.querySelector("#myName").innerText = myName;
  let myProfile = data.userProfile;
  document.querySelector("#myProfile").src = ".." + myProfile;
  let mySeq = data.userSeq;
  document.querySelector("#mySeq").innerText = mySeq;
  let myRegisterDate = data.userRegisterDate;
  document.querySelector("#myregisterDate").innerText = myRegisterDate;
  let myUserClsf = data.userClsf;
  document.querySelector("#myUserClsf").innerText = myUserClsf;
}

async function getEvent() {
  let response = await fetch(contextPath + "/event/getEvent");
  let data = await response.json(); //==Json.parse();

  let length = data.length;
  let eventWrapper = document.querySelector("#event-wrapper");
  if (length > 0) {
    for (i = 0; i < length; i++) {
      let event = data[i];
      let div = document.createElement("div");

      if (i == 0) {
        div.className = "carousel-item active";
      } else {
        div.className = "carousel-item";
      }
      let a = document.createElement("a");
      a.setAttribute("href", event.url);
      let img = document.createElement("img");
      img.className = "d-block w-100";
      img.setAttribute("src", event.img);
      a.appendChild(img);
      div.appendChild(a);
      eventWrapper.appendChild(div);
    }
  } else {
  }
}
