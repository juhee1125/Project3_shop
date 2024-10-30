package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.ProductVO;

public interface ProductMapper {
	public List<ProductVO> productlist();
	public void productInsert(ProductVO product);
	public void productUpdate(ProductVO product);
	public void productDelete(long p_num);
	public ProductVO getProductByNum(long p_num);
	public List<ProductVO> findByCategory(String p_category);
	public Long findByName(String p_name);
}
