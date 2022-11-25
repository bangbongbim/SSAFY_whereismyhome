package com.mycom.myapp.region.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.info.dto.DongCodeDto;
import com.mycom.myapp.info.dto.GugunCodeDto;

@Mapper
public interface RegionDao {
	List<GugunCodeDto> findGugunCodeList(String cityName);
	List<DongCodeDto> findDongCodeList(String gugunName);
}
