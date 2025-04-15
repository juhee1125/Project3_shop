package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.CouponDownloadVO;

public interface CouponDownloadMapper {
	public List<CouponDownloadVO> coupondownloadlist();
	public void coupondownloadInsert(CouponDownloadVO coupondownload);
//	public void couponMnumUpdate(CouponVO coupon);
//	public void couponDelete (long c_num);
	public List<CouponDownloadVO> getCouponByCnum(long c_num);
//	public List<Long> findQnAByQNum(String q_title);
	public List<Long> findCNumByMnum(long m_num);
//	public List<QnAVO> datesearch(Map<String, Object> paramMap);
}
