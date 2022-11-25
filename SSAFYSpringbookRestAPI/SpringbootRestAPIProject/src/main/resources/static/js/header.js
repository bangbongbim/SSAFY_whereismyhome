const token = sessionStorage.getItem("accessToken")
console.log(token)


// 로그인 상태
if (token) {
    document.querySelector("#element-login").style.display = "none";
    document.querySelector("#element-register").style.display = "none";
    document.querySelector("#element-userInfo").style.display = "inline-block";
    document.querySelector("#element-logout").style.display = "inline-block";
}

// 로그인 하지 않은 상태
else {
    document.querySelector("#element-login").style.display = "inline-block";
    document.querySelector("#element-register").style.display = "inline-block";
    document.querySelector("#element-userInfo").style.display = "none";
    document.querySelector("#element-logout").style.display = "none";
}


const logout = async()=>{
    const url ="/logout"

    try{
        let response = await fetch(url)
        let data = await response.json();
        console.log(data)

        if(data.result == "success"){
        sessionStorage.removeItem("accessToken");
        location.href="/"
        }

    }
    catch(error){
        console.log(error)
    }
}

    document.querySelector("#element-logout").addEventListener("click",logout)
