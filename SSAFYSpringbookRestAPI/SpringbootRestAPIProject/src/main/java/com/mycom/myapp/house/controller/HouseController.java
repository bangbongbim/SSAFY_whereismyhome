package com.mycom.myapp.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.house.service.HouseService;
import com.mycom.myapp.info.dto.HouseDealDetailDto;
import com.mycom.myapp.info.dto.HouseDealSimpleDto;

@RestController
@CrossOrigin(
		// localhost:5500 과 127.0.0.1 구분
		origins = "http://localhost:5500", // allowCredentials = "true" 일 경우, orogins="*" 는 X
		allowCredentials = "true", 
		allowedHeaders = "*", 
		methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.HEAD,RequestMethod.OPTIONS}
	)
public class HouseController {

	@Autowired
	HouseService service;
	
	@GetMapping("/house/dong")
	private List<HouseDealSimpleDto> houseListByDong(int limit, int offset, String dong) {
		System.out.println("/house/dong 들어옴");
		
		List<HouseDealSimpleDto> list= service.findHouseDealsByDongCode(dong, limit, offset);
		System.out.println(list);
		return list;
	}
	
	@GetMapping("/house/dong/total")
	private int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto) {
		System.out.println("/house/dong/total 들어옴");
		System.out.println(houseDealDto);
		int cnt=service.houseDealsByDongCodeTotalCnt(houseDealDto);
		System.out.println(cnt);
		return cnt;
	}
	
	@GetMapping("/house/apart")
	private List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset) {
		System.out.println("/house/apart 들어옴");
		
		List<HouseDealSimpleDto> list = service.findHouseDealsByAptName(searchWord, limit, offset);
		System.out.println(list);
		return list;
	}
	
	
	@GetMapping("/house/apart/total")
	private int houseDealsByAptNameTotalCnt(String searchWord) {
		System.out.println("/house/apart/total 들어옴 "+searchWord);
		
		int cnt=service.houseDealsByAptNameTotalCnt(searchWord);
		System.out.println(cnt+"개");
		return cnt;
	}
	
	@GetMapping("/house/{no}")
	private HouseDealDetailDto findHouseDealDetail(@PathVariable int no) {
		System.out.println("/house/{"+ no +"} 들어옴 ");
		HouseDealDetailDto dto=service.findHouseDealDetail(no);
		System.out.println(dto);
		return dto;
	}
	
	//변화 테스트용
	@GetMapping("/house/search")
	private List<HouseDealSimpleDto> searchHouseDeal(String sigugun, String dong, String searchWord){
		System.out.println("집 검색!!"+sigugun +", "+dong+", "+searchWord);
		List<HouseDealSimpleDto> list=null;
		
		if((sigugun==null ) && ( searchWord==null )) {
			//searchAll
			System.out.println("searchAll");
			list = service.searchAll();
		}else if((sigugun==null ) && !(searchWord==null )) {
			//searchByName
			System.out.println("searchByName");
			list = service.searchByName(searchWord);
		}else if((dong==null ) && (searchWord==null ) ) {
			//searchBySigugun
			System.out.println("searchBySigugun");
			list = service.searchBySigugun(sigugun);
		}else if((dong==null ) && !(searchWord==null )) {
			//searchBySigugunName
			System.out.println("searchBySigugunName");
			list = service.searchBySigugunName(sigugun, searchWord);
		}else if(!(dong==null ) && (searchWord==null )) {
			//searchByDong
			System.out.println("searchByDong");
			list = service.searchByDong(dong);
		}else if(!(dong==null ) && !(searchWord==null )) {
			//searchByDongName
			System.out.println("searchByDongName");
			list = service.searchByDongName(dong, searchWord);
		}
		
		return list;
	}
	
}
