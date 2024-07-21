package com.travel.seoul.service;

import java.util.List;

import com.travel.seoul.vo.ProductVO;

public interface ProductService {
	public void productinsert(ProductVO vo);
	public List<ProductVO> productlist();
	public List<ProductVO> findByName(String product_name);
	public List<ProductVO> findByCategory(String product_category);
	ProductVO getProductBynum(Long product_num);
	public void productupdate(ProductVO vo);
	public void productnameDelete(String product_name);
};
