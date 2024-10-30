package com.travel.seoul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.seoul.mapper.UserMapper;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Service
public class UserServiceImpl implements UserService{
	
	@Setter(onMethod_ = @Autowired)
	private UserMapper mapper;

	@Override
	public List<UserVO> list() {
		return mapper.list();
	}

	@Override
	public void Insert(UserVO user) {
		mapper.Insert(user);
	}

	@Override
	public UserVO selectID(String m_id) {
		return mapper.selectID(m_id);
	}

	@Override
	public void PWupdate(UserVO user) {
		mapper.PWupdate(user);
	}

	@Override
	public void Delete(long m_num) {
		mapper.Delete(m_num);
	}

	@Override
	public UserVO getByNum(long m_num) {
		return mapper.getByNum(m_num);
	}
	

}
