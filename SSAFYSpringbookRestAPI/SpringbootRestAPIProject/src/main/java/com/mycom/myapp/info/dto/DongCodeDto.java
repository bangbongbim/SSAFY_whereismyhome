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
public class DongCodeDto {
	private String code;
	private String name;
	private String cityCode;
	private String cityName;
	private String gugunCode;
	private String gugunName;

}
