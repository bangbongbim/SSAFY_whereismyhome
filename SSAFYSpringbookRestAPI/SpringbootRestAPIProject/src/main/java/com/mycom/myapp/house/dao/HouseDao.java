package com.mycom.myapp.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.myapp.info.dto.HouseDealDetailDto;
import com.mycom.myapp.info.dto.HouseDealSimpleDto;


@Mapper
public interface HouseDao {
	List<HouseDealSimpleDto> findHouseDealsByDongCode(@Param("dong")String dong, @Param("limit")int limit, @Param("offset")int offset);
	int houseDealsByDongCodeTotalCnt(HouseDealSimpleDto houseDealDto);
	List<HouseDealSimpleDto> findHouseDealsByAptName(@Param("searchWord")String searchWord,  @Param("limit")int limit, @Param("offset")int offset);
	int houseDealsByAptNameTotalCnt(String searchWord);
	HouseDealDetailDto findHouseDealDetail(int no);
	
	List<HouseDealSimpleDto> searchAll();
	List<HouseDealSimpleDto> searchByName(String searchWord);
	List<HouseDealSimpleDto> searchBySigugun(String sigugun);
	List<HouseDealSimpleDto> searchBySigugnName(@Param("sigugun")String sigugun, @Param("searchWord")String searchWord);
	List<HouseDealSimpleDto> searchByDong(String dong);
	List<HouseDealSimpleDto> searchByDongName(@Param("dong")String dong, @Param("searchWord")String searchWord);
}
