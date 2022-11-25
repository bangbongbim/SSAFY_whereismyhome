package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

public interface UserService {
	UserDto userLogin(UserDto dto);

	UserResultDto userRegister(UserDto dto);

	UserResultDto userModify(UserDto dto);

	UserResultDto userDelete(int userSeq);
	

}
