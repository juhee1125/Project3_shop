package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.UserVO;

public interface UserMapper {
	public void insert(UserVO vo);
	public List<UserVO> list();
	public UserVO selectID(String id);
	public void PWupdate(UserVO vo);
	public void IDDelete(String id);
}
