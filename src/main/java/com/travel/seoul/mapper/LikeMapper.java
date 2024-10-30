package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.LikeVO;

public interface LikeMapper {
	public List<LikeVO> likelist();
	public void likeInsert(LikeVO like);
	public void likeDelete(long l_num);
	public LikeVO getLikeByNum(long l_num);
	public List<Long> findLikeByLNum(long m_num);
	public List<Long> findLikeByPNum(long p_num);
}
