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
	public void productinsert(ProductVO vo) {
		System.out.println("상품등록 : " + vo);
		pmapper.productinsert(vo);
	}
	@Override
	public List<ProductVO> productlist() {
		System.out.println("상품목록");
		return pmapper.productlist();
	}
	@Override
	public List<ProductVO> findByName(String product_name) {
		System.out.println("수정할 상품"+product_name);
		return pmapper.findByName(product_name);
	}
	@Override
	public void productnameDelete(String product_name) {
		System.out.println("상품삭제");
		pmapper.productnameDelete(product_name);
	}
	@Override
	public List<ProductVO> findByCategory(String product_category) {
		return pmapper.findByCategory(product_category);
	}
	@Override
	public ProductVO getProductBynum(Long product_num) {
		return pmapper.getProductBynum(product_num);
	}
	@Override
	public void productupdate(ProductVO vo) {
		pmapper.productupdate(vo);
		
	}
}
