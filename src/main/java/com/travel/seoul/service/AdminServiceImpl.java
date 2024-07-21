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
	 @Override public void adminInsert(AdminVO admin) {
		 amapper.adminInsert(admin);
	 }
	 @Override public int adminUpdate(AdminVO admin) {
		 return amapper.adminUpdate(admin); 
	 }
	 @Override public AdminVO getAdminByNum(long admin_num) {
		 return amapper.getAdminByNum(admin_num);
	 }
	 @Override public void adminIDDelete(String admin_id) {
		 amapper.adminIDDelete(admin_id);
	 }
	@Override
	public AdminVO getAdminID(String admin_id) {
		return amapper.getAdminID(admin_id);
	}
 }
