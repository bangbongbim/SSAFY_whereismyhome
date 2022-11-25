package com.mycom.myapp.region.service;

import java.util.List;


import com.mycom.myapp.info.dto.DongCodeDto;
import com.mycom.myapp.info.dto.GugunCodeDto;

public interface RegionService {
	List<GugunCodeDto> findGugunCodeList(String cityName);
	List<DongCodeDto> findDongCodeList(String gugunName);
}
