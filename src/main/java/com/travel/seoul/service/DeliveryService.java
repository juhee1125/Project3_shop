package com.travel.seoul.service;

import java.util.List;

import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.vo.QnAVO;

public interface DeliveryService { 
	List<OrderVO> Productinquiry(String startDate, String endDate);
	List<QnAVO> Productqnainquiry(String startDate, String endDate);
}

