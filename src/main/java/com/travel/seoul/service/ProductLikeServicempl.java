package com.travel.seoul.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.travel.seoul.mapper.LikeMapper;
import com.travel.seoul.vo.LikeVO;
import com.travel.seoul.vo.UserVO;

@Service
public class ProductLikeServicempl implements ProductLikeService{
	@Autowired
	private LikeMapper LikeMapper;

	@Override
	public ResponseEntity<String> ProductLike(UserVO user, @RequestBody Map<String, String> userData) {
		if (user==null) {
    		System.out.println("로그인 먼저");
    		return ResponseEntity.ok("로그인 후 가능합니다");
    	}
    	else {
    		String likeaction = userData.get("likeaction");
    		long productNum = Long.parseLong(userData.get("productNum"));

    		if(likeaction.equals("liked")) {
    			System.out.println("좋아요");
				LikeVO likeplus = new LikeVO();
    			likeplus.setP_num(productNum);
    			likeplus.setM_num(user.getM_num());
    			LikeMapper.likeInsert(likeplus);
    			
    			return ResponseEntity.ok("좋아요");
    		}
    		else {
    			System.out.println("좋아요 해제");
				LikeMapper.likeDelete(productNum);
				System.out.println("좋아요 삭제");
    			return ResponseEntity.ok("좋아요 삭제");
    		}	
    	}
	}
}

