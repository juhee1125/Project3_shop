package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.ProductOptionVO;

public interface ProductOptionMapper {
	public void optioninsert(ProductOptionVO vo);
	public List<ProductOptionVO> optionlist();
	public List<ProductOptionVO> findByoptionName(String option_name);
	public void optionupdate(ProductOptionVO vo);
	public void optionnameDelete(String option_name);
	public void optionnumDelete(Long option_num);
}
