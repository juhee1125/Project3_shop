package com.travel.seoul.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.travel.seoul.vo.UserVO;

public interface ProductLikeService { 
	ResponseEntity<String> ProductLike(UserVO user, @RequestBody Map<String, String> userData);
}

