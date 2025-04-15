package com.travel.seoul.mapper;

import java.util.List;
import java.util.Map;

import com.travel.seoul.vo.OrderVO;

public interface OrderMapper {
	public List<OrderVO> orderlist();
	public void orderInsert(OrderVO order);
	public void orderoptionUpdate(OrderVO order);
	public void paymentstatusUpdate(OrderVO order);
	public void orderDelete(long o_num);
	public void orderpnumDelete(long p_num);
	public OrderVO getOrderByNum(long o_num);
	public List<OrderVO> getOrderByONum(long m_num);
	public List<OrderVO> datesearch(Map<String, Object> paramMap);
	public List<OrderVO> onumbersearch(String o_number);
}
