package com.travel.seoul.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.vo.QnAVO;

@Service
public class DeliveryServicempl implements DeliveryService{
	@Autowired
	private OrderMapper OrderMapper;
	@Autowired
	private QnAMapper QnAMapper;

	@Override
	public List<OrderVO> Productinquiry(String startDate, String endDate) {
		Map<String, Object> datesearchmap = new HashMap<>();
		datesearchmap.put("startDate", startDate);
		datesearchmap.put("endDate", endDate);
		List<OrderVO> datesearchlist = OrderMapper.datesearch(datesearchmap);
		
		return datesearchlist;
	}
	
	@Override
	public List<QnAVO> Productqnainquiry(String startDate, String endDate) {
		Map<String, Object> datesearchmap = new HashMap<>();
		datesearchmap.put("startDate", startDate);
		datesearchmap.put("endDate", endDate);
		List<QnAVO> datesearchlist = QnAMapper.datesearch(datesearchmap);
		
		return datesearchlist;
	}
}

