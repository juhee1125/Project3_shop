package com.travel.seoul.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.vo.ProductVO;

import lombok.Setter;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Setter(onMethod_ = @Autowired)
	private ProductMapper pmapper;

	@Override
	public List<ProductVO> productlist() {
		return pmapper.productlist();
	}

	@Override
	public void productInsert(ProductVO product) {
		pmapper.productInsert(product);
	}

	@Override
	public void productUpdate(ProductVO product) {
		pmapper.productUpdate(product);
	}

	@Override
	public void productDelete(long p_num) {
		pmapper.productDelete(p_num);
	}

	@Override
	public ProductVO getProductByNum(long p_num) {
		return pmapper.getProductByNum(p_num);
	}

	@Override
	public List<ProductVO> findByCategory(String p_category) {
		return pmapper.findByCategory(p_category);
	}

	
}
