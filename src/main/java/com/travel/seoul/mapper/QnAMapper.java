package com.travel.seoul.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.travel.seoul.vo.QnAVO;

public interface QnAMapper {
	public void qnainsert(QnAVO vo);
	public List<QnAVO> qnalist();
	public List<QnAVO> findByID(@Param("q_id") String q_id, @Param("q_title") String q_title);
}
