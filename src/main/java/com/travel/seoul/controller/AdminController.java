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

import com.travel.seoul.mapper.AdminMapper;
import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.AdminVO;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	@Setter(onMethod_=@Autowired) 
	private UserService service;

	@Autowired
    private AdminMapper adminMapper;

	//회원관리
	@GetMapping("/memberList")
	public String memberList(Model model) {
		List<UserVO> list = service.list();
		//덮어씌워지는 것을 방지하기 위해 list에 값 저장
		List<String> roles = new ArrayList<>();
		for (UserVO user : list) {		
			Long a_num = adminMapper.findAdminByANum(user.m_num);
			AdminVO role = null;
    
			if (a_num != null) {
			    role = adminMapper.getAdminByNum(a_num);
			}
			
			//관리자 DB에 userID가 없을 경우 일반회원, 있을 경우 관리자
			if (role != null && role.m_num == user.m_num) {
				roles.add("관리자");
			}
			else {
				roles.add("일반회원");
			}
		}
		model.addAttribute("roles", roles);
		model.addAttribute("list", list);
		return "/admin/memberList";
	}	
	//관리자 권한 부여
	@PostMapping(value = "/adminupdate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> adminupdate(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			//객체 생성 후 값 저장
            AdminVO admin = new AdminVO();
		    admin.setM_num(num);
		    
		    //관리자 DB에 값 추가
		    adminMapper.adminInsert(admin);
		}
	    return ResponseEntity.ok("관리자로 승급하였습니다");
	}	
	//관리자 권한 제거
	@PostMapping(value = "/admindelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> admindelete(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			//관리자 DB에 값 삭제 
			adminMapper.adminDelete(adminMapper.findAdminByANum(num));
		}
	    return ResponseEntity.ok("일반회원이 되었습니다");
	}
	
	//회원탈퇴
	@PostMapping(value = "/userdelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> userdelete(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			long num = Long.parseLong(userlist.get("num"));
			
			//회원 DB와 연결된 데이터 모두 삭제
			if (adminMapper.findAdminByANum(num)!=null) {
				adminMapper.adminDelete(adminMapper.findAdminByANum(num));
				service.Delete(num);
			}
			else{
				service.Delete(num);
			}
		}
		return ResponseEntity.ok("회원탈퇴되었습니다");
	}
	
	//검색
	@GetMapping("/search")
	public String search(@RequestParam("topic") String topic, @RequestParam("keyword") String keyword, HttpSession session) {
		List<UserVO> list = service.list();
		List<UserVO> searchList = new ArrayList<>();
		
		//키워드가 해당 주제에 포함되어있으면 List에 추가
		for (UserVO user:list) {
			switch (topic) {
				case "id":
	                if (keyword.equals(user.getM_id())) {
	                	searchList.add(user);
	                }
	                break;
				case "name":
					if (keyword.equals(user.getM_name())) {
						searchList.add(user);
					}
					break;
				 case "area":
					if (keyword.equals(user.getM_adress()) || keyword.equals(user.getM_detailAddress()) || keyword.equals(user.getM_postalcode())) {
						searchList.add(user);
					}
					break;
				 default:
		                System.out.println("결과없음");
		            break;
			}
		}
		session.setAttribute("user_searchList", searchList);
		
		return "admin/memberList";
	}
}
