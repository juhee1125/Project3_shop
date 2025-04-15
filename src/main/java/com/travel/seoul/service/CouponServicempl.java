package com.travel.seoul.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.seoul.mapper.CouponDownloadMapper;
import com.travel.seoul.mapper.CouponMapper;
import com.travel.seoul.mapper.OrderMapper;
import com.travel.seoul.vo.CouponVO;
import com.travel.seoul.vo.OrderVO;
import com.travel.seoul.mapper.QnAMapper;
import com.travel.seoul.vo.QnAVO;
import com.travel.seoul.vo.UserVO;

@Service
public class CouponServicempl implements CouponService{
	@Autowired
	private CouponDownloadMapper CouponDownloadMapper;
	@Autowired
	private CouponMapper CouponMapper;

	@Override
	public List<CouponVO> PossessionCoupon(long mnum) {
		List<CouponVO> usercouponlist = new ArrayList<>();
		
		List<Long>cnumlist = CouponDownloadMapper.findCNumByMnum(mnum);		
		for(Long cnum : cnumlist) {
			CouponVO coupon = CouponMapper.getCouponByNum(cnum);
			if(coupon.c_useYN==true) {
				usercouponlist.add(coupon);
			}
		}
		
		List<CouponVO> couponlist = CouponMapper.coupontypeUserlist();
		for(CouponVO coupon:couponlist) {
			if(coupon.c_useYN==true) {
				usercouponlist.add(coupon);
			}
		}
		
		return usercouponlist;
	}
}

