package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.LikeVO;

public interface LikeMapper {
	public void likeinsert(LikeVO vo);
	public List<LikeVO> likelist();
	public List<LikeVO> findByID(String like_id);
	public void likenameDelete(String like_name);
}
