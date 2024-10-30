package com.travel.seoul.service;

import java.util.List;

import com.travel.seoul.vo.ProductVO;

public interface ProductService {
	public List<ProductVO> productlist();
	public void productInsert(ProductVO product);
	public void productUpdate(ProductVO product);
	public void productDelete(long p_num);
	public ProductVO getProductByNum(long p_num);
	public List<ProductVO> findByCategory(String p_category);
};
