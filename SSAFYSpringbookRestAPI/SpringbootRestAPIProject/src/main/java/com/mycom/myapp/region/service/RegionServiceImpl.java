package com.mycom.myapp.region.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.info.dto.DongCodeDto;
import com.mycom.myapp.info.dto.GugunCodeDto;
import com.mycom.myapp.region.dao.RegionDao;

@Service
public class RegionServiceImpl implements RegionService{

	@Autowired
	RegionDao dao;
	
	@Override
	public List<GugunCodeDto> findGugunCodeList(String cityName) {
		List<GugunCodeDto> list = new ArrayList<>();
		try {
			 list= dao.findGugunCodeList(cityName);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<DongCodeDto> findDongCodeList(String gugunName) {
		List<DongCodeDto> list = new ArrayList<>();
		try {
			 list= dao.findDongCodeList(gugunName);
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
