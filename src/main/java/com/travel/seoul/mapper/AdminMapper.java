package com.travel.seoul.mapper;
 
import java.util.List;

import com.travel.seoul.vo.AdminVO;

public interface AdminMapper {
	public List<AdminVO> adminlist(); 
	public void adminInsert(AdminVO admin);
	public int adminUpdate(AdminVO admin);
	public void adminIDDelete(String admin_id);
	public AdminVO getAdminByNum(long admin_num);
	public AdminVO getAdminID(String admin_id);
	}

