package com.travel.seoul.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.travel.seoul.vo.ProductOptionVO;

public interface ProductOptionMapper {
	public List<ProductOptionVO> optionlist();
	public void optionInsert(ProductOptionVO option);
	public void optionUpdate(ProductOptionVO option);
	public void optionDelete(long po_num);
	public void optionpnumDelete(long p_num);
	public ProductOptionVO getOptionByNum(long po_num);
	public List<Long> findOptionByPONum(long p_num);
	public Long findOptionAndDetail(@Param("p_num") long p_num, @Param("po_option") String po_option, @Param("po_optiondetail") String po_optiondetail);
}
