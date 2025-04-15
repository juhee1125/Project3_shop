package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.ReviewPathVO;

public interface ReviewPathMapper {
	public List<ReviewPathVO> reviewpathlist();
	public void reviewpathInsert(ReviewPathVO reviewpath);
	public List<ReviewPathVO> findPathByNum (Long r_num);
}
