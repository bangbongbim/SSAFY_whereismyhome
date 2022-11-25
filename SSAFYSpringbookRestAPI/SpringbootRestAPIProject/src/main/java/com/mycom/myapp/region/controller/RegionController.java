package com.mycom.myapp.region.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.info.dto.DongCodeDto;
import com.mycom.myapp.info.dto.GugunCodeDto;
import com.mycom.myapp.region.service.RegionService;

@RestController
@CrossOrigin(
		// localhost:5500 과 127.0.0.1 구분
		origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
		allowCredentials = "true", 
		allowedHeaders = "*", 
		methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
public class RegionController {
	
	@Autowired
	RegionService service;
	
	@GetMapping("/region/gugun/{cityName}")
	private List<GugunCodeDto> findGugunCodeList(@PathVariable String cityName){
		System.out.println("/region/gugun/{"+ cityName + "} 들어옴");
		List<GugunCodeDto> list = service.findGugunCodeList(cityName);
		System.out.println(list);
		return list;
	}
	
	@GetMapping("/region/dong/{gugunName}")
	private List<DongCodeDto> findDongCodeList(@PathVariable String gugunName){
		System.out.println("/region/dong/{"+ gugunName + "} 들어옴");
		List<DongCodeDto> list = service.findDongCodeList(gugunName);
		System.out.println(list);
		return list;
	}
}
