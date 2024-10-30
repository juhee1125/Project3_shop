package com.travel.seoul.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.seoul.service.UserService;
import com.travel.seoul.vo.UserVO;

import lombok.Setter;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Setter(onMethod_=@Autowired) 
	private UserService service;
	
	//회원가입
	@GetMapping("/register")
	public String register() {
		return "/register";
	}
	@PostMapping(value = "/registerprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> registerprocess(@RequestBody Map<String, String> userData) {
	    System.out.println("회원가입 controller");
	    //Ajax로 데이터를 받을 때 String로 변환하여 받아야 함
	    String userName = userData.get("userName");
		String userID = userData.get("userID");
	    String userPW = userData.get("userPW");
	    String userPhone = userData.get("userPhone");
	    String userEmail = userData.get("userEmail");
	    String emailinput = userData.get("emailinput");
	    String userAddress = userData.get("userAddress");
	    String Postalcode = userData.get("Postalcode");
	    String userdetailAddress = userData.get("userdetailAddress");
	    System.out.println("userName"+userName);
	    
	    UserVO user = new UserVO();
	    //필수입력 항목 확인
	    if (userName.isEmpty() || userID.isEmpty() || userPW.isEmpty() || userPhone.isEmpty() || userAddress.isEmpty() 
	    		|| Postalcode.isEmpty() || userdetailAddress.isEmpty()) {
	    	System.out.println("회원가입 실패");   
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수항목을 입력해주세요");
	    }
	    //DB에 회원정보 저장
	    else {
	    	user.setM_name(userName);
	        user.setM_id(userID);
	        user.setM_pw(userPW);
	        user.setM_phone(userPhone);
	        user.setM_postalcode(Postalcode);
	        user.setM_adress(userAddress);
	        user.setM_detailAddress(userdetailAddress);
	        if (userEmail.isEmpty()) {
	            user.setM_email(null);
	        } else {
	            user.setM_email(userEmail+ "@" +emailinput);
	        }
	        user.setM_Rating(null);
	        service.Insert(user);
	    	System.out.println("회원가입 성공");
	        return ResponseEntity.ok("회원가입을 완료했습니다");
	    }
	}
    
	//아이디 중복확인
	@PostMapping(value = "/duplicateprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> duplicateprocess(@RequestBody Map<String, String> userData, Model model) {
		String userID = userData.get("userID");
		model.addAttribute("userID",userID);
		if (service.selectID(userID) != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용중인 아이디입니다");
		}
		else if("".equals(userID)) {
			return ResponseEntity.ok("아이디를 입력해주세요");
		}
		else {
			return ResponseEntity.ok("사용가능한 아이디입니다");  
		}
	}
	
	
	//로그인
	@GetMapping("/login")
	public String login() {
		return "/login";    
	}
	@PostMapping("/loginprocess")
	public ResponseEntity<String> loginprocess(@RequestBody Map<String, String> userData, HttpSession session) {
	    System.out.println("로그인 controller");
		String userID = userData.get("userID");
	    String userPW = userData.get("userPW");
	    
		List<UserVO> list = service.list();

	    for (UserVO user : list) {
	    	if (user.getM_id().equals(userID) && user.getM_pw().equals(userPW)) {
		        session.setAttribute("loginMember", user);
		        System.out.println(" 로그인 성공 :" +userID+","+userPW);
		        return ResponseEntity.ok("로그인 성공");
		      }
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디 또는 비번이 일치하지 않습니다");  
	}
    //로그아웃
    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    	System.out.println("로그아웃");
        return "redirect:/"; 
    }  
    
    
    //아이디 찾기
    @GetMapping("/findID")
  	public String findID() {
  	    return "/findID"; 
  	}
    @PostMapping(value = "/findIDprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<String> findIDprocess(@RequestBody Map<String, String> userData) {
  		String userName = userData.get("userName");
  		String userPhone = userData.get("userPhone");
  		
  		System.out.println("아이디 찾기 controller");
  		List<UserVO> list = service.list(); 
  		for (UserVO user : list) {
			if (user.getM_name().equals(userName) && user.getM_phone().equals(userPhone)) {
				return ResponseEntity.ok(user.getM_id());
			}
  		}
  		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일치하는 회원정보가 없습니다");  
  	}
    //비밀번호 찾기
  	@GetMapping("/findPW")
  	public String findPW() {
  	    return "/findPW"; 
  	}
  	@PostMapping(value = "/findPWprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<String> findPWprocess(@RequestBody Map<String, String> userData, HttpSession session) {
  		System.out.println(userData);
  		String userID = userData.get("userID");
  		String userName = userData.get("userName");
  		String userPhone = userData.get("userPhone");
  		
  		session.setAttribute("PWchangeID", userID);
  		
  		System.out.println("비밀번호 찾기 controller");
  		System.out.println("userID"+ userID+"userName"+userName+"userPhone"+userPhone);
  		
  		if (service.selectID(userID)!=null) {		
  			System.out.println(service.selectID(userID));
  			if (service.selectID(userID).getM_name().equals(userName) && service.selectID(userID).getM_phone().equals(userPhone)) {
  				return ResponseEntity.ok("ok");
  			}
  			else if (!service.selectID(userID).getM_name().equals(userName)) {
  				return ResponseEntity.ok("이름이 일치하지 않습니다");
  			}
  			else if (!service.selectID(userID).getM_phone().equals(userPhone)) {
  				return ResponseEntity.ok("핸드폰번호가 일치하지 않습니다");
  			}
  		}
  		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디가 일치하지 않습니다");  
  	}
  	//비밀번호 변경
  	@GetMapping("/PWchange")
  	public String PWchange() {
  	    return "/PWchange"; 
  	}
  	@PostMapping(value = "/PWchangeprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  	public ResponseEntity<String> PWchangeprocess(@RequestBody Map<String, String> userData, HttpSession session) {
  		System.out.println("비밀번호 변경완료");
  		//바꿀 비밀번호
  		String userPW = userData.get("userPW");
  		String PWchangeID = (String) session.getAttribute("PWchangeID");
  		
  		List<UserVO> list = service.list(); 
  		for (UserVO user : list) {
  			if(user.getM_id().equals(PWchangeID)) {
  				UserVO newPW = new UserVO();
  				newPW.setM_pw(userPW);
  				newPW.setM_id(PWchangeID);
  				service.PWupdate(newPW);
  			}
  		}
  		return ResponseEntity.ok("비밀번호가 변경되었습니다");
  	}
}