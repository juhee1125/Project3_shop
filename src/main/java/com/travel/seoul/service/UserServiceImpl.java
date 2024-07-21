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
	public void insert(UserVO vo) {
		System.out.println("서비스에서 로그인 : " + vo);
		mapper.insert(vo);
	}

	@Override
	public List<UserVO> list() {
		System.out.println("서비스에서 회원가입");
		return mapper.list();
	}
	@Override
	public UserVO selectID(String id) {
		System.out.println("서비스 아이디 중복 확인 : "+id);
		return mapper.selectID(id);
	}
	@Override
	public void PWupdate(UserVO vo) {
		System.out.println("비번 변경");
		mapper.PWupdate(vo);
	}

	@Override
	public void IDDelete(String id) {
		System.out.println("회원탈퇴");
		mapper.IDDelete(id);
	}
}
