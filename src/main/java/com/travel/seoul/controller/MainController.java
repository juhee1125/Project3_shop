package com.travel.seoul.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.seoul.mapper.AdminMapper;
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
	
	@GetMapping("/")
	public String main() {		
		return "user/Main";
	}	
	@GetMapping("/main")
	public String mainprocess(HttpSession session, UserVO vo) {
		UserVO user = (UserVO) session.getAttribute("loginMember");
		List<AdminVO> Adminlist = adminMapper.adminlist();
		String AdminID = null;
		String Adminname = null;
		for (AdminVO admin : Adminlist) {
			if (user.getM_id().equals(admin.getA_id())) {
				AdminID = admin.getA_id();
				Adminname = admin.getA_name();
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
			return "forward:/recommend";
		}
	}
	
	private static final String UPLOAD_DIR = "C://upload//";
    @GetMapping("/display/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> displayImage(@PathVariable String fileName) {
        System.out.println("Requested file name: " + fileName);
        File file = new File(UPLOAD_DIR + fileName);

        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            Resource resource = new FileSystemResource(file);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }	
}
