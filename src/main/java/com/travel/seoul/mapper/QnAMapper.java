package com.travel.seoul.mapper;

import java.util.List;
import java.util.Map;

import com.travel.seoul.vo.QnAVO;

public interface QnAMapper {
	public List<QnAVO> qnalist();
	public void qnaInsert(QnAVO qna);
	public void QnAUpdate(QnAVO qna);
	public void QnADelete (long q_num);
	public QnAVO getQnaByNum(long q_num);
	public List<Long> findQnAByQNum(String q_title);
	public List<Long> findMNumByQNum(long m_num);
	public List<QnAVO> datesearch(Map<String, Object> paramMap);
}
