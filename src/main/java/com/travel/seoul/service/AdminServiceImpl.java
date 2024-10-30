package com.travel.seoul.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.vo.AdminVO;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService {
	 
	 @Setter(onMethod_ = @Autowired)
	 AdminMapper amapper;

	@Override
	public List<AdminVO> adminlist() {
		return amapper.adminlist();
	}

	@Override
	public void adminInsert(AdminVO admin) {
		amapper.adminInsert(admin);
	}

	@Override
	public void adminUpdate(AdminVO admin) {
		amapper.adminUpdate(admin);
	}

	@Override
	public void adminDelete(long a_num) {
		amapper.adminDelete(a_num);
	}

	@Override
	public AdminVO getAdminByNum(long a_num) {
		return amapper.getAdminByNum(a_num);
	}
	 

 }
