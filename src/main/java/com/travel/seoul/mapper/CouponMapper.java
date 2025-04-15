package com.travel.seoul.mapper;

import java.util.List;

import com.travel.seoul.vo.CouponVO;

public interface CouponMapper {
	public List<CouponVO> couponlist();
	public void couponInsert(CouponVO coupon);
	public void couponuseYNUpdate(long c_num);
	public void couponDelete (long c_num);
	public List<CouponVO> coupontypeUserlist();
	public CouponVO getCouponByNum(long c_num);
//	public List<Long> findQnAByQNum(String q_title);
	public Long findCNumByPNum(long p_num);
//	public List<QnAVO> datesearch(Map<String, Object> paramMap);
}
