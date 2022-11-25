package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = "/")
	public String index() {
		return "/html/index.html";
	}

	@GetMapping(value = "/login")
	public String login() {
		return "/html/login.html";
	}

	@GetMapping(value = "/register")
	public String register() {
		return "/html/register.html";
	}

	@GetMapping(value = "/modifyUserInfo")
	public String modifyUserInfo() {
		return "/html/user/modifyUserInfo.html";
	}

	@GetMapping(value = "/dealByApart")
	public String dealByApart() {
		return "/html/dealByApart.html";
	}
	
	@GetMapping(value = "/dealByDong")
	public String dealByDong() {
		return "/html/dealByDong.html";
	}

}
