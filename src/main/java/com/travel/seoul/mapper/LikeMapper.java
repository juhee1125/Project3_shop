package com.travel.seoul.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.travel.seoul.vo.LikeVO;

@Mapper
public interface LikeMapper {
	public List<LikeVO> likelist();
	public void likeInsert(LikeVO like);
	public void likeDelete(long l_num);
	public void likepnumDelete(long p_num);
	public LikeVO getLikeByNum(long l_num);
	public List<Long> findLikeByLNum(long m_num);
	public List<Long> findLikeByPNum(long p_num);
}
