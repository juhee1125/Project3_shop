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
import com.travel.seoul.service.ProductLikeService;
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
	@Autowired
	private ProductLikeService ProductLikeService;
	
	//스킨케어 상품
    @GetMapping("/skin")
    public String skin(Model model, HttpSession session) {
    	List<ProductVO> skincategorylist = ProductMapper.findByCategory("skin");
    	List<String> skinpathlist = new ArrayList<>();
    	for (ProductVO list : skincategorylist) {
    		List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(list.getP_num());
    		Long firstpath = pp_num_list.get(0);
    		ProductPathVO path = ProductPathMapper.getPathByNum(firstpath);
    		skinpathlist.add(path.getPp_path());
    	}
    	model.addAttribute("skincategorylist", skincategorylist);
    	model.addAttribute("skinpathlist", skinpathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	
    	if (user!=null && LikeMapper.findLikeByLNum(user.getM_num())!=null) {
    		List<Long> l_num_list = LikeMapper.findLikeByLNum(user.getM_num());
    		List<LikeVO> likenumlist = new ArrayList<>();
    		for (Long l_num : l_num_list) {
    			likenumlist.add(LikeMapper.getLikeByNum(l_num));
    		}
    		List<LikeVO> likelist = (List<LikeVO>) likenumlist;
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(ProductMapper.getProductByNum(likeproduct.getP_num()).p_name);
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Skin";
    }
    //선케어 상품
    @GetMapping("/sun")
    public String sun(Model model, HttpSession session) {
    	List<ProductVO> suncategorylist = ProductMapper.findByCategory("sun");
    	List<String> sunpathlist = new ArrayList<>();
    	for (ProductVO list : suncategorylist) {
    		List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(list.getP_num());
    		Long firstpath = pp_num_list.get(0);
    		ProductPathVO path = ProductPathMapper.getPathByNum(firstpath);
    		sunpathlist.add(path.getPp_path());
    	}
    	model.addAttribute("suncategorylist", suncategorylist);
    	model.addAttribute("sunpathlist", sunpathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	
    	if (user!=null && LikeMapper.findLikeByLNum(user.getM_num())!=null) {
    		List<Long> l_num_list = LikeMapper.findLikeByLNum(user.getM_num());
    		List<LikeVO> likenumlist = new ArrayList<>();
    		for (Long l_num : l_num_list) {
    			likenumlist.add(LikeMapper.getLikeByNum(l_num));
    		}
    		List<LikeVO> likelist = (List<LikeVO>) likenumlist;
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(ProductMapper.getProductByNum(likeproduct.getP_num()).p_name);
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Sun";
    }
    //베이스메이크업 상품
    @GetMapping("/base")
    public String base(Model model, HttpSession session) {
    	List<ProductVO> basecategorylist = ProductMapper.findByCategory("base");
    	List<String> basepathlist = new ArrayList<>();
    	for (ProductVO list : basecategorylist) {
    		List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(list.getP_num());
    		Long firstpath = pp_num_list.get(0);
    		ProductPathVO path = ProductPathMapper.getPathByNum(firstpath);
    		basepathlist.add(path.getPp_path());
    	}
    	model.addAttribute("basecategorylist", basecategorylist);
    	model.addAttribute("basepathlist", basepathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	
    	if (user!=null && LikeMapper.findLikeByLNum(user.getM_num())!=null) {
    		List<Long> l_num_list = LikeMapper.findLikeByLNum(user.getM_num());
    		List<LikeVO> likenumlist = new ArrayList<>();
    		for (Long l_num : l_num_list) {
    			likenumlist.add(LikeMapper.getLikeByNum(l_num));
    		}
    		List<LikeVO> likelist = (List<LikeVO>) likenumlist;
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(ProductMapper.getProductByNum(likeproduct.getP_num()).p_name);
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Base";
    }
    //아이메이크업 상품
    @GetMapping("/eye")
    public String eye(Model model, HttpSession session) {
    	List<ProductVO> eyecategorylist = ProductMapper.findByCategory("eye");
    	List<String> eyepathlist = new ArrayList<>();
    	for (ProductVO list : eyecategorylist) {
    		List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(list.getP_num());
    		Long firstpath = pp_num_list.get(0);
    		ProductPathVO path = ProductPathMapper.getPathByNum(firstpath);
    		eyepathlist.add(path.getPp_path());
    	}
    	model.addAttribute("eyecategorylist", eyecategorylist);
    	model.addAttribute("eyepathlist", eyepathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	
    	if (user!=null && LikeMapper.findLikeByLNum(user.getM_num())!=null) {
    		List<Long> l_num_list = LikeMapper.findLikeByLNum(user.getM_num());
    		List<LikeVO> likenumlist = new ArrayList<>();
    		for (Long l_num : l_num_list) {
    			likenumlist.add(LikeMapper.getLikeByNum(l_num));
    		}
    		List<LikeVO> likelist = (List<LikeVO>) likenumlist;
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(ProductMapper.getProductByNum(likeproduct.getP_num()).p_name);
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Eye";
    }
    //립메이크업 상품
    @GetMapping("/lip")
    public String lip(Model model, HttpSession session) {
    	List<ProductVO> lipcategorylist = ProductMapper.findByCategory("lip");
    	List<String> lippathlist = new ArrayList<>();
    	for (ProductVO list : lipcategorylist) {
    		List<Long> pp_num_list = ProductPathMapper.findPathByPPNum(list.getP_num());
    		Long firstpath = pp_num_list.get(0);
    		ProductPathVO path = ProductPathMapper.getPathByNum(firstpath);
    		lippathlist.add(path.getPp_path());
    	}
    	model.addAttribute("lipcategorylist", lipcategorylist);
    	model.addAttribute("lippathlist", lippathlist);
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");
    	
    	if (user!=null && LikeMapper.findLikeByLNum(user.getM_num())!=null) {
    		List<Long> l_num_list = LikeMapper.findLikeByLNum(user.getM_num());
    		List<LikeVO> likenumlist = new ArrayList<>();
    		for (Long l_num : l_num_list) {
    			likenumlist.add(LikeMapper.getLikeByNum(l_num));
    		}
    		List<LikeVO> likelist = (List<LikeVO>) likenumlist;
    		List<String> likeproductlist = new ArrayList<>();
    		for (LikeVO likeproduct : likelist) {
    			likeproductlist.add(ProductMapper.getProductByNum(likeproduct.getP_num()).p_name);
    		}
    		model.addAttribute("likeproductlist", likeproductlist);
    	}
    	
    	return "user/Lip";
    }
    
    //상세페이지
    @GetMapping("/detail")
    public String detail(@RequestParam(value="productname", required = false) String productname) {
    	return "user/ShopDetail";
    }
    
    //좋아요 기능
    @PostMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> likes(HttpSession session, @RequestBody Map<String, String> userData) {
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");

    	return ProductLikeService.ProductLike(user, userData);
    }
}