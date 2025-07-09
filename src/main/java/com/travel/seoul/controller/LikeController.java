package com.travel.seoul.controller;

import java.util.ArrayList;
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
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	//로그인 안되었을 때
    	if(user==null) {
    		return "/user/loginfirst";
    	}
    	else {
    		List<LikeVO> likelist = LikeMapper.likelist();
			
        	List<ProductVO> productlist = new ArrayList<>();
        	List<String> likepathlist = new ArrayList<>();
        	for (LikeVO like : likelist) {
        		if (like.getM_num()==user.getM_num()) {
        			likepathlist.add(ProductPathMapper.getPathByPath(like.getP_num()).get(0));
        			productlist.add(ProductMapper.getProductByNum(like.getP_num()));
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
		String productname = userData.get("productName");
		
		long p_num = ProductMapper.findByName(productname);
		List<Long> l_num_list = LikeMapper.findLikeByPNum(p_num);
		for (Long l_num : l_num_list) {
			LikeMapper.likeDelete(l_num);
		}

    	return ResponseEntity.ok("좋아요 삭제");
    }
    
    //좋아요 페이지 카테고리
    @GetMapping("/likeselect")
	public String likeselect(@RequestParam("job") String job, HttpSession session) {
		List<ProductVO> selectList = new ArrayList<>();
		List<String> likecategorypathlist = new ArrayList<>();
		
		List<ProductVO> productlist = (List<ProductVO>) session.getAttribute("productlist");
		//키워드가 해당 주제에 포함되어있으면 List에 추가
		switch (job) {
			case "skin":				
				for (ProductVO product : productlist) {	
					List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(ProductMapper.findByName(product.getP_name()));
                    if ("skin".equals(product.getP_category())) {
                        selectList.add(product);
                        for (Long pp_num : pp_num_list) {
                        	likecategorypathlist.add(ProductPathMapper.getPathByNum(pp_num).getPp_path());
                        }
                    }
                }
				break;		
			 case "sun":
				 for (ProductVO product : productlist) {	
					List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(ProductMapper.findByName(product.getP_name()));
                    if ("sun".equals(product.getP_category())) {
                        selectList.add(product);
                        for (Long pp_num : pp_num_list) {
                        	likecategorypathlist.add(ProductPathMapper.getPathByNum(pp_num).getPp_path());
                        }
                    }
                }
				break;
			 case "base":
				 for (ProductVO product : productlist) {	
					List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(ProductMapper.findByName(product.getP_name()));
                    if ("base".equals(product.getP_category())) {
                        selectList.add(product);
                        for (Long pp_num : pp_num_list) {
                        	likecategorypathlist.add(ProductPathMapper.getPathByNum(pp_num).getPp_path());
                        }
                    }
                }
				break;
			 case "eye":
				 for (ProductVO product : productlist) {	
					List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(ProductMapper.findByName(product.getP_name()));
                    if ("eye".equals(product.getP_category())) {
                        selectList.add(product);
                        for (Long pp_num : pp_num_list) {
                        	likecategorypathlist.add(ProductPathMapper.getPathByNum(pp_num).getPp_path());
                        }
                    }
                }
				break;
			 case "lip":
				 for (ProductVO product : productlist) {	
					List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(ProductMapper.findByName(product.getP_name()));
                    if ("lip".equals(product.getP_category())) {
                        selectList.add(product);
                        for (Long pp_num : pp_num_list) {
                        	likecategorypathlist.add(ProductPathMapper.getPathByNum(pp_num).getPp_path());
                        }
                    }
                }
				break;
		}
		session.setAttribute("like_selectList", selectList);
		session.setAttribute("like_categorypathList", likecategorypathlist);
		
		return "/user/Like";
	}
}