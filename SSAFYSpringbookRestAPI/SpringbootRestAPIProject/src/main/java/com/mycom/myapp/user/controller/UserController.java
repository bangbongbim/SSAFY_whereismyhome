package com.mycom.myapp.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	private final int SUCCESS = 1;

	@PostMapping(value = "/register")
	public ResponseEntity<Map<String, String>> register(UserDto dto) {

		System.out.println("회원가입 정보 : " + dto.toString());
		UserResultDto userResultDto = userService.userRegister(dto);

		Map<String, String> map = new HashMap<>();
		if (userResultDto.getResult() == SUCCESS) {
			map.put("result", "success");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, String>> login(UserDto dto, HttpSession session) {

		if (session.getAttribute("userDto") != null)
			System.out.println("세션이 존재함 ");

		UserDto userDto = userService.userLogin(dto);

		Map<String, String> map = new HashMap<>();

		System.out.println("로그인 정보 : " + userDto.toString());

		if (userDto != null) {
			session.setAttribute("userDto", userDto);
			map.put("result", "success");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/logout")
	public ResponseEntity<Map<String, String>> logout(HttpSession session) {
		Map<String, String> map = new HashMap<>();
		session.invalidate();

		map.put("result", "success");

		return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}

	// 사용자 정보
	@GetMapping(value = "/user")
	public ResponseEntity<UserDto> getUserInfo(UserDto dto, HttpSession session) {

		UserDto userDto = (UserDto) session.getAttribute("userDto");

		System.out.println("session Data :" + userDto);

		if (userDto != null) {
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDto>(userDto, HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping(value = "/user")
	public ResponseEntity<Map<String, String>> modify(UserDto dto, HttpSession session) {
		System.out.println("변경 될 회원 정보 : " + dto.toString());

		UserDto userDto = (UserDto) session.getAttribute("userDto");

		Map<String, String> map = new HashMap<>();

		System.out.println("기존 회원 정보 : " + userDto.toString());

		// session에 들어있는 userSeq를 변경되는 userDto에 넣어줌
		dto.setUserSeq(userDto.getUserSeq());
		UserResultDto userResultDto = userService.userModify(dto);

		if (userResultDto.getResult() == SUCCESS) {
			map.put("result", "success");
			session.setAttribute("userDto", dto);
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/user")
	public ResponseEntity<Map<String, String>> delete(HttpSession session) {
		Map<String, String> map = new HashMap<>();
		UserDto userDto = (UserDto) session.getAttribute("userDto");

		System.out.println("삭제 될 회원 UserDto :" + userDto);

		int userSeq = userDto.getUserSeq();

		System.out.println("삭제 될 UserSeq : " + userSeq);

		UserResultDto userResultDto = userService.userDelete(userSeq);
		if (userResultDto.getResult() == SUCCESS) {
			map.put("result", "success");
			session.invalidate();
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} else {
			map.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
		}

	}

}
