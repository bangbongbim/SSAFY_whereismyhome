package com.mycom.myapp.house.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mycom.myapp.info.dto.HouseDealDetailDto;
import com.mycom.myapp.info.dto.HouseDealSimpleDto;

public interface HouseService {
	List<HouseDealSimpleDto> findHouseDealsByDongCode(String dong, int limit, int offset);
	int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto);
	List<HouseDealSimpleDto> findHouseDealsByAptName(String searchWord, int limit, int offset);
	int houseDealsByAptNameTotalCnt(String searchWord);
	
	HouseDealDetailDto findHouseDealDetail(int no);
	
	List<HouseDealSimpleDto> searchAll();
	List<HouseDealSimpleDto> searchByName(String searchWord);
	List<HouseDealSimpleDto> searchBySigugun(String sigugun);
	List<HouseDealSimpleDto> searchBySigugunName(String sigugun, String searchWord);
	List<HouseDealSimpleDto> searchByDong(String dong);
	List<HouseDealSimpleDto> searchByDongName(String dong, String searchWord);
}
