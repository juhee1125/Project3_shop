//package com.travel.seoul.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.lang.reflect.Type;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import com.travel.seoul.mapper.LikeMapper;
//import com.travel.seoul.mapper.ProductMapper;
//import com.travel.seoul.mapper.ProductPathMapper;
//import com.travel.seoul.service.RecommendAPIService;
//import com.travel.seoul.service.UserService;
//import com.travel.seoul.vo.LikeVO;
//import com.travel.seoul.vo.ProductPathVO;
//import com.travel.seoul.vo.ProductVO;
//import com.travel.seoul.vo.UserVO;
//
//import lombok.Setter;
//
//@Controller
//public class RecommendController {
//	@Setter(onMethod_=@Autowired)
//	private UserService service;
//	
//	@Autowired
//    private RecommendAPIService recommendservice;
//	@Autowired
//	private LikeMapper likemapper;
//	@Autowired
//	private ProductPathMapper ProductPathMapper;
//	@Autowired
//	private ProductMapper ProductMapper;
//	
//	@GetMapping("/recommend")
//    public String recommendAPI(Model model, HttpServletRequest request, HttpSession session) {	
//		System.out.println("recommend 실행");
//		List<LikeVO> list = likemapper.likelist();
//		
//		List<Map<String, String>> formattedList = new ArrayList<>();
//	    for (LikeVO like : list) {
//	        Map<String, String> likeMap = new HashMap<>();
//	        likeMap.put("l_name", ProductMapper.getProductByNum(like.getP_num()).getP_name());
//	        likeMap.put("l_id", service.getByNum(like.getM_num()).getM_id());
//	        formattedList.add(likeMap);
//	    }
//
//		Gson gson = new GsonBuilder().create();
//		//리스트 json으로 변환
//		String jsonList = gson.toJson(formattedList);
//		
//		UserVO user = (UserVO) session.getAttribute("loginMember");
//		String loginuser = "";
//
//		if (user == null || user.equals("") || likemapper.findLikeByLNum(user.getM_num()) == null) {
//		    loginuser = "notlogin";
//		} else if (likemapper.likelist() == null || likemapper.likelist().isEmpty()) {
//		    loginuser = "notlogin";
//		} else {
//		    List<Long> l_num_list = likemapper.findLikeByLNum(user.getM_num());
//		    List<LikeVO> likeloginlist =new ArrayList<>();
//		    for (Long l_num : l_num_list) {
//		    	if (likemapper.getLikeByNum(l_num) == null) {
//			        loginuser = "notlogin";
//			    } else {
//			        loginuser = user.getM_id();
//			        likeloginlist.add(likemapper.getLikeByNum(l_num));
//			        model.addAttribute("likeloginlist", likeloginlist);
//			        System.out.println("likeloginlist: "+likeloginlist);
//			    }	
//		    }
//		}
//
//		String likeAPI = recommendservice.recommendAPI("http://localhost:5000/recommend", "like", jsonList, loginuser, "500", "json", "20240804");
//		System.out.println("likeAPI: "+likeAPI);
//		//json list로 변환
//		Type listType = new TypeToken<List<String>>(){}.getType();
//        List<String> stringList = gson.fromJson(likeAPI, listType);
//        
//        List<ProductPathVO> pathlist = ProductPathMapper.pathlist();
//        List<Long> pnumlist = new ArrayList<>();
//        Map<String, String> productPathMap = new LinkedHashMap<>();
//	    // pathlist를 순회하며 첫 번째 경로만 저장
//	    for (ProductPathVO path : pathlist) {
//	    	ProductVO productlist = ProductMapper.getProductByNum(path.getP_num());
//	        String productName = productlist.getP_name();
//	        if (stringList.contains(productName) && !productPathMap.containsKey(productName)) {
//	            productPathMap.put(productName, path.getPp_path());
//	            model.addAttribute("productp_num", path.getP_num());
//	            pnumlist.add(productlist.getP_num());
//	        }
//	    }
//	    System.out.println("productPathMap: "+productPathMap);
//	    model.addAttribute("productPathMap", productPathMap);
//	    model.addAttribute("pnumlist", pnumlist);
//	    
//	    List<ProductVO> productlist = ProductMapper.productlist();
//	    Set<String> setprice = new HashSet<>();
//	    Set<String> setdiscount = new HashSet<>();
//	    List<String> listprice = new ArrayList<>();
//	    List<String> listdiscount = new ArrayList<>();
//	    
//	    for (ProductVO product : productlist) {
//	    	String productName = product.getP_name();
//	    	if (stringList.contains(productName)) {
//	    		if (setprice.add(product.getP_price())) {
//	                listprice.add(product.getP_price());
//	            }
//	            if (setdiscount.add(product.getP_discount())) {
//	                listdiscount.add(product.getP_discount());
//	            }
//	    	}
//	    }
//	    System.out.println("setprice: "+setprice);
//	    System.out.println("setdiscount: "+setdiscount);
//	    model.addAttribute("listprice", listprice);
//	    model.addAttribute("listdiscount", listdiscount);
//
//        
//    	return "/user/Main";
//    }
//}
