package com.mycom.myapp.info.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseDealDetailDto {
	// 아파트 거래 정보
	private int no;
	private String dong;
	private String cityName; // dong 테이블 정보
	private String gugunName; // dong 테이블 정보
	private String aptName;
	private String code;
	private String dealAmount;
	private String buildYear;
	private String dealYear;
	private String dealMonth;
	private String dealDay;
	private String area;
	private String floor;
	private String jibun;
	private String type;
	private String rentMoney;
	private int houseNo;
	
	// 아파트 정보
//	private int no;
//	private String dong;
//	private String aptName;
//	private String code;
//	private String buildYear;
//	private String jibun;
	private String lat;
	private String lng;
	private String img;
	
	
}
