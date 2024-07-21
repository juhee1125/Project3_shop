package com.travel.seoul.service;

import java.util.List;

import com.travel.seoul.vo.UserVO;

public interface UserService {
	public void insert(UserVO vo);
	public List<UserVO> list();
	public UserVO selectID(String id);	//중복 Id 검색
	public void PWupdate(UserVO vo); //비밀번호 변경
	public void IDDelete(String id);
};
