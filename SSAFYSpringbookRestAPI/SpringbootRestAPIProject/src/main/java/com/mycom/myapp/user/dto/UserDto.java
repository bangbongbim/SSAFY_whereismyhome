package com.mycom.myapp.user.dto;

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
public class UserDto {
	private int userSeq;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userProfileImage;
	private Date userRegisterDate;
	private String userClsf;
}
