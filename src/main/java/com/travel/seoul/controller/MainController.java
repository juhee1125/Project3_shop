package com.travel.seoul.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.service.ProductLikeService;
import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
public class MainController {
	@Setter(onMethod_=@Autowired)
	private UserService service;
	
	@Autowired
    private AdminMapper adminMapper;
	@Autowired
	private ProductLikeService ProductLikeService;
	
	@GetMapping("/")
	public String main() {		
//		return "forward:/recommend";
		return "/user/Main";
	}	
	@GetMapping("/main")
	public String mainprocess(HttpSession session, UserVO vo) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
		List<AdminVO> Adminlist = adminMapper.adminlist();
		String AdminID = null;
		String Adminname = null;
		if (user == null || user.equals("")) {
			return "redirect:/";
		}
		for (AdminVO admin : Adminlist) {
			if (user.getM_num()==admin.getM_num()) {
				AdminID = user.getM_id();
				Adminname = user.getM_name();
				break;
			}
		}		
		// user와 admindbID가 null이 아니고 userID와 adminID가 동일할 때 adminID 세션에 저장
		if (AdminID !=null) {
			System.out.println("관리자로그인");
	        session.setAttribute("AdminID", AdminID);
	        session.setAttribute("Adminname", Adminname);
	        System.out.println("로그인 되었어요"+Adminlist);
	        return "redirect:/admin/memberList";
		} else {
			System.out.println("일반회원로그인");
			System.out.println("로그인 되었어요"+user);
			return "/user/Main";
		}
	}
	
	//좋아요 기능
    @PostMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> likes(HttpSession session, @RequestBody Map<String, String> userData) {
    	
    	UserVO user = (UserVO) session.getAttribute("loginMember");

    	return ProductLikeService.ProductLike(user, userData);
    }
}
