package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.UserVO;

public interface UserMapper {
	public List<UserVO> list();
	public void Insert(UserVO user);
	public UserVO selectID(String m_id);
	public void PWupdate(UserVO user);
	public void Delete(long m_num);
	public UserVO getByNum(long m_num);
}