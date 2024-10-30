package com.travel.seoul.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.travel.seoul.vo.QnAVO;

public interface QnAMapper {
	public List<QnAVO> qnalist();
	public void qnaInsert(QnAVO qna);
	public void QnAUpdate(QnAVO qna);
	public QnAVO getQnaByNum(long q_num);
}
