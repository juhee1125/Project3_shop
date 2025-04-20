package com.travel.seoul.mapper;
 
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.travel.seoul.vo.AdminVO;

@Mapper
public interface AdminMapper {
	public List<AdminVO> adminlist(); 
	public void adminInsert(AdminVO admin);
	public void adminUpdate(AdminVO admin);
	public void adminDelete(long a_num);
	public AdminVO getAdminByNum(long a_num);
	public Long findAdminByANum(long m_num);
	}


