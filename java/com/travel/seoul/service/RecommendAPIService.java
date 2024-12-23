package com.travel.seoul.service;

import java.util.List;

import com.travel.seoul.vo.LikeVO;

public interface RecommendAPIService {
	String recommendAPI(String urlStr, String select, String value, String value2, String numOfRows, String apiType, String status_dt);
}
