package com.travel.seoul.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.mapper.LikeMapper;
import com.travel.seoul.vo.LikeVO;
import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/like/*")
public class LikeController {
	
	@Setter(onMethod_=@Autowired) 
	private ProductMapper ProductMapper;
	
	@Autowired
	private ProductPathMapper ProductPathMapper;
	
	@Autowired
	private LikeMapper LikeMapper;
	
	//좋아요
    @GetMapping("/like")
    public String like(Model model, HttpSession session) {
    	System.out.println("좋아요 controller");

    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user==null) {
    		return "/user/loginfirst";
    	}
    	else {
    		List<LikeVO> likelist = LikeMapper.likelist();
        	List<ProductVO> productlist = new ArrayList<>();
        	List<String> likepathlist = new ArrayList<>();
        	for (LikeVO like : likelist) {
        		if (like.getL_id().equals(user.getM_id())) {
        			likepathlist.add(ProductPathMapper.findBypathName(like.getL_name()).get(0).getPp_path());
        			productlist.addAll(ProductMapper.findByName(like.getL_name()));
        		}
        	}
        	model.addAttribute("productlist", productlist);
        	session.setAttribute("productlist", productlist);
        	model.addAttribute("likepathlist", likepathlist);
        	
        	return "/user/Like";
    	}
    }
    @PostMapping(value = "/likeprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> likeprocess(@RequestBody Map<String, String> userData) {
    	System.out.println("좋아요 해제");
		String productname = userData.get("productName");
		System.out.println(productname);
		LikeMapper.likenameDelete(productname);
    	return ResponseEntity.ok("좋아요 삭제");
    }
    
    //좋아요 페이지 카테고리
    @GetMapping("/likeselect")
	public String likeselect(@RequestParam("job") String job, HttpSession session) {
		System.out.println("카테고리선택 controller");
		
		List<ProductVO> selectList = new ArrayList<>();
		List<String> likecategorypathlist = new ArrayList<>();
		
		List<ProductVO> productlist = (List<ProductVO>) session.getAttribute("productlist");
		
		//키워드가 해당 주제에 포함되어있으면 List에 추가
		switch (job) {
			case "skin":				
				for (ProductVO product : productlist) {
                    if ("skin".equals(product.getP_category())) {
                        selectList.add(product);
                        likecategorypathlist.add(ProductPathMapper.findBypathName(product.getP_name()).get(0).getPp_path());
                    }
                }
				break;		
			 case "sun":
				for (ProductVO product : productlist) {
                    if ("sun".equals(product.getP_category())) {
                        selectList.add(product);
                        likecategorypathlist.add(ProductPathMapper.findBypathName(product.getP_name()).get(0).getPp_path());
                    }
                }
				break;
			 case "base":
				for (ProductVO product : productlist) {
                    if ("base".equals(product.getP_category())) {
                        selectList.add(product);
                        likecategorypathlist.add(ProductPathMapper.findBypathName(product.getP_name()).get(0).getPp_path());
                    }
                }
				break;
			 case "eye":
				for (ProductVO product : productlist) {
                    if ("eye".equals(product.getP_category())) {
                        selectList.add(product);
                        likecategorypathlist.add(ProductPathMapper.findBypathName(product.getP_name()).get(0).getPp_path());
                    }
                }
				break;
			 case "lip":
				for (ProductVO product : productlist) {
                    if ("lip".equals(product.getP_category())) {
                        selectList.add(product);
                        likecategorypathlist.add(ProductPathMapper.findBypathName(product.getP_name()).get(0).getPp_path());
                    }
                }
				break;
			 default:
	                System.out.println("전체좋아요리스트");
	            break;
		}
		session.setAttribute("like_selectList", selectList);
		session.setAttribute("like_categorypathList", likecategorypathlist);
		
		return "/user/Like";
	}
}