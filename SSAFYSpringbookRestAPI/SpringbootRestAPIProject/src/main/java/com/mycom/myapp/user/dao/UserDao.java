package com.mycom.myapp.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

@Mapper
public interface UserDao {

	UserDto userLogin(String userEmail);

	int userRegister(UserDto dto);

	int userModify(UserDto dto);

	int userDelete(int userSeq);

	UserDto userInfo(UserDto dto);
}
