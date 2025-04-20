package com.travel.seoul.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.travel.seoul.vo.ReviewVO;

@Mapper
public interface ReviewMapper {
	public List<ReviewVO> reviewlist();
	public void reviewInsert(ReviewVO review);
	public void reviewpnumDelete (long p_num);
	public List<ReviewVO> findReviewByNum (Long p_num);
}
