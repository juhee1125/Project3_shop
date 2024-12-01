package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;

public interface ProductPathMapper {
	public List<ProductPathVO> pathlist();
	public void pathInsert(ProductPathVO path);
	public void pathUpdate(ProductPathVO path);
	public void pathDelete(long pp_num);
	public void pathpnumDelete(long p_num);
	public ProductPathVO getPathByNum(long pp_num);
	public List<Long> findPathByPPNum(long p_num);
	public List<String> getPathByPath(long p_num);
}
