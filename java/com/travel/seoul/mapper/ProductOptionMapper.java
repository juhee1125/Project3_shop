package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.ProductOptionVO;
import com.travel.seoul.vo.ProductPathVO;

public interface ProductOptionMapper {
	public List<ProductOptionVO> optionlist();
	public void optionInsert(ProductOptionVO option);
	public void optionUpdate(ProductOptionVO option);
	public void optionDelete(long po_num);
	public void optionpnumDelete(long p_num);
	public ProductOptionVO getOptionByNum(long po_num);
	public List<Long> findOptionByPONum(long p_num);
}
