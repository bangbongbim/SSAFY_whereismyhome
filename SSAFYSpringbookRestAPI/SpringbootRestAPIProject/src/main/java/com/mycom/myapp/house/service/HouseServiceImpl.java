package com.mycom.myapp.house.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.house.dao.HouseDao;
import com.mycom.myapp.info.dto.HouseDealDetailDto;
import com.mycom.myapp.info.dto.HouseDealSimpleDto;



@Service
public class HouseServiceImpl implements HouseService{

	@Autowired
	HouseDao dao;
	
	@Override
	public List<HouseDealSimpleDto> findHouseDealsByDongCode(String dong, int limit, int offset) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.findHouseDealsByDongCode(dong, limit, offset);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto) {
		int ret=-1;
		try {
			 ret= dao.houseDealsByDongCodeTotalCnt(houseDealDto);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.findHouseDealsByAptName(searchWord, limit, offset);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int houseDealsByAptNameTotalCnt(String searchWord) {
		int ret=-1;
		try {
			 ret= dao.houseDealsByAptNameTotalCnt(searchWord);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public HouseDealDetailDto findHouseDealDetail(int no) {
		HouseDealDetailDto dto = new HouseDealDetailDto();
		try {
			 dto=dao.findHouseDealDetail(no);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<HouseDealSimpleDto> searchAll() {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.searchAll();
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<HouseDealSimpleDto> searchByName(String searchWord) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.searchByName(searchWord);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<HouseDealSimpleDto> searchBySigugun(String sigugun) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.searchBySigugun(sigugun);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<HouseDealSimpleDto> searchBySigugunName(String sigugun, String searchWord) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.searchBySigugnName(sigugun, searchWord);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<HouseDealSimpleDto> searchByDong(String dong) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.searchByDong(dong);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<HouseDealSimpleDto> searchByDongName(String dong, String searchWord) {
		List<HouseDealSimpleDto> list = new ArrayList<>();
		try {
			 list= dao.searchByDongName(dong, searchWord);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
