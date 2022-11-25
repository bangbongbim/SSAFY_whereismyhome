package com.mycom.myapp.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dao.UserDao;
import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	private final int SUCCESS = 1;
	private final int FAIL = -1;

	@Override
	public UserDto userLogin(UserDto dto) {
		UserDto userDto = userDao.userLogin(dto.getUserEmail());

//		System.out.println("userDto :" + userDto.toString());
//		System.out.println("dto :" + dto.toString());
		if (userDto != null && userDto.getUserPassword().contentEquals(dto.getUserPassword())) {
			return userDto;
		}
		return null;
	}

	@Override
	public UserResultDto userRegister(UserDto dto) {
		UserResultDto userResultDto = new UserResultDto();

		if (userDao.userRegister(dto) == 1) {
			userResultDto.setResult(SUCCESS);
		} else {
			userResultDto.setResult(FAIL);
		}

		return userResultDto;
	}

	@Override
	public UserResultDto userModify(UserDto dto) {
		System.out.println("servuceImpl DTO : " + dto.toString());
		UserResultDto userResultDto = new UserResultDto();

		if (userDao.userModify(dto) == 1) {
			userResultDto.setResult(SUCCESS);
		} else {
			userResultDto.setResult(FAIL);
		}

		return userResultDto;
	}

	@Override
	public UserResultDto userDelete(int userSeq) {

		UserResultDto userResultDto = new UserResultDto();

		if (userDao.userDelete(userSeq) == 1) {
			userResultDto.setResult(SUCCESS);
		} else {
			userResultDto.setResult(FAIL);
		}
		return userResultDto;
	}

}
