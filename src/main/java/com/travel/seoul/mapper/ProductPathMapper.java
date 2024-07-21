package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.ProductPathVO;

public interface ProductPathMapper {
	public void pathinsert(ProductPathVO vo);
	public List<ProductPathVO> pathlist();
	public List<ProductPathVO> findBypathName(String path_name);
	public void pathupdate(ProductPathVO vo);
	public void pathnameDelete(String path_name);
}
