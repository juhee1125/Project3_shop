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
import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/shop/*")
public class ShopController {
	
	@Setter(onMethod_=@Autowired) 
	private ProductMapper ProductMapper;
	
	@Autowired
	private ProductPathMapper ProductPathMapper;
	
	@Autowired
	private LikeMapper LikeMapper;
	
	//스킨케어 상품
    @GetMapping("/skin")
    public String skin(Model model, HttpSession session) {
    	List<ProductVO> skincategorylist = ProductMapper.findByCategory("skin");
    	System.out.println("skincategorylist: "+skincategorylist);
    	List<String> skinpathlist = new ArrayList<>();
    	for (ProductVO list : skincategorylist) {
    		List<ProductPathVO> paths = ProductPathMapper.findBypathName(list.getP_name());
    		skinpathlist.add(paths.get(0).getPp_path());
    	}
    	System.out.println("skinpathlist: "+skinpathlist);
    	model.addAttribute("skincategorylist", skincategorylist);
    	model.addAttribute("skinpathlist", skinpathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user!=null) {
    		List<LikeVO> likelist = LikeMapper.findByID(user.getM_id());
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(likeproduct.getL_name());
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Skin";
    }
    //선케어 상품
    @GetMapping("/sun")
    public String sun(Model model, HttpSession session) {
    	List<ProductVO> suncategorylist = ProductMapper.findByCategory("sun");
    	System.out.println("suncategorylist: "+suncategorylist);
    	List<String> sunpathlist = new ArrayList<>();
    	for (ProductVO list : suncategorylist) {
    		List<ProductPathVO> paths = ProductPathMapper.findBypathName(list.getP_name());
    		sunpathlist.add(paths.get(0).getPp_path());
    	}
    	System.out.println("sunpathlist: "+sunpathlist);
    	model.addAttribute("suncategorylist", suncategorylist);
    	model.addAttribute("sunpathlist", sunpathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user!=null) {
    		List<LikeVO> likelist = LikeMapper.findByID(user.getM_id());
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(likeproduct.getL_name());
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Sun";
    }
    //베이스메이크업 상품
    @GetMapping("/base")
    public String base(Model model, HttpSession session) {
    	List<ProductVO> basecategorylist = ProductMapper.findByCategory("base");
    	System.out.println("basecategorylist: "+basecategorylist);
    	List<String> basepathlist = new ArrayList<>();
    	for (ProductVO list : basecategorylist) {
    		List<ProductPathVO> paths = ProductPathMapper.findBypathName(list.getP_name());
    		basepathlist.add(paths.get(0).getPp_path());
    	}
    	System.out.println("basepathlist: "+basepathlist);
    	model.addAttribute("basecategorylist", basecategorylist);
    	model.addAttribute("basepathlist", basepathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user!=null) {
    		List<LikeVO> likelist = LikeMapper.findByID(user.getM_id());
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(likeproduct.getL_name());
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Base";
    }
    //아이메이크업 상품
    @GetMapping("/eye")
    public String eye(Model model, HttpSession session) {
    	List<ProductVO> eyecategorylist = ProductMapper.findByCategory("eye");
    	System.out.println("eyecategorylist: "+eyecategorylist);
    	List<String> eyepathlist = new ArrayList<>();
    	for (ProductVO list : eyecategorylist) {
    		List<ProductPathVO> paths = ProductPathMapper.findBypathName(list.getP_name());
    		eyepathlist.add(paths.get(0).getPp_path());
    	}
    	System.out.println("eyepathlist: "+eyepathlist);
    	model.addAttribute("eyecategorylist", eyecategorylist);
    	model.addAttribute("eyepathlist", eyepathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user!=null) {
    		List<LikeVO> likelist = LikeMapper.findByID(user.getM_id());
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(likeproduct.getL_name());
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Eye";
    }
    //베이스메이크업 상품
    @GetMapping("/lip")
    public String lip(Model model, HttpSession session) {
    	List<ProductVO> lipcategorylist = ProductMapper.findByCategory("lip");
    	System.out.println("lipcategorylist: "+lipcategorylist);
    	List<String> lippathlist = new ArrayList<>();
    	for (ProductVO list : lipcategorylist) {
    		List<ProductPathVO> paths = ProductPathMapper.findBypathName(list.getP_name());
    		lippathlist.add(paths.get(0).getPp_path());
    	}
    	System.out.println("lippathlist: "+lippathlist);
    	model.addAttribute("lipcategorylist", lipcategorylist);
    	model.addAttribute("lippathlist", lippathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	if(user!=null) {
    		List<LikeVO> likelist = LikeMapper.findByID(user.getM_id());
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(likeproduct.getL_name());
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Lip";
    }
    
    //상세페이지
    @GetMapping("/detail")
    public String detail(@RequestParam(value="productname", required = false) String productname) {
    	System.out.println("productname: "+productname);
    	return "user/ShopDetail";
    }
    
    //좋아요 기능
    @PostMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> likes(HttpSession session, @RequestBody Map<String, String> userData) {
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");

		if (user==null) {
    		System.out.println("로그인 먼저");
    		return ResponseEntity.ok("로그인 후 가능합니다");
    	}
    	else {
    		String likeaction = userData.get("likeaction");
    		String productName = userData.get("productName");

    		if(likeaction.equals("liked")) {
    			System.out.println("좋아요");
				LikeVO likeplus = new LikeVO();
    			likeplus.setL_name(productName);
    			likeplus.setL_id(user.getM_id());
    			LikeMapper.likeinsert(likeplus);
    			
    			return ResponseEntity.ok("좋아요");
    		}
    		else {
    			System.out.println("좋아요 해제");
				System.out.println("좋아요 삭제");
				LikeMapper.likenameDelete(productName);
				
    			return ResponseEntity.ok("좋아요 삭제");
    		}	
    	}
    }
}