package com.travel.seoul.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.seoul.mapper.ProductMapper;
import com.travel.seoul.mapper.ProductOptionMapper;
import com.travel.seoul.mapper.ProductPathMapper;
import com.travel.seoul.vo.ProductOptionVO;
import com.travel.seoul.vo.ProductPathVO;
import com.travel.seoul.vo.ProductVO;

import lombok.Setter;

@Controller
@RequestMapping("/admin/*")
public class AdminProductController {
	
	@Setter(onMethod_=@Autowired) 
	private ProductMapper ProductMapper;

	@Autowired
	private ProductOptionMapper ProductOptionMapper;
	
	@Autowired
	private ProductPathMapper ProductPathMapper;
	
	//상품목록	
	@GetMapping("/productList")
	public String productList(Model model) {
        List<ProductVO> productlist = ProductMapper.productlist();
        model.addAttribute("productlist", productlist);
        List<ProductOptionVO> productoptionlist = ProductOptionMapper.optionlist();
        model.addAttribute("productoptionlist", productoptionlist);
		return "/admin/productList";
	}
	
	//상품등록	
	@GetMapping("/productupload")
	public String productupload() {        
		return "/admin/productupload";
	}
	private static final String UPLOAD_DIR = "D://upload"; 
	@PostMapping(value = "/productuploadprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> productuploadprocess(@RequestParam("imageFiles") List<MultipartFile> imageFiles,
	@RequestParam("detailImageFile") MultipartFile detailImageFile, @RequestParam("nameinput") String nameinput,
	@RequestParam("priceinput") String priceinput, @RequestParam("quantityinput") String quantityinput,
	@RequestParam("percentinput") String percentinput, @RequestParam("dateinputstart") String dateinputstart,
	@RequestParam("dateinputend") String dateinputend, @RequestParam("optioninput") String optioninput,
	@RequestParam("optiondetailinput") String optiondetailinput, @RequestParam("categoryradio") String categoryradio) {
		System.out.println("등록 controller");
		List<ProductVO> productList = ProductMapper.productlist();
		Map<String, String> response = new HashMap<>();
	    
        for (ProductVO product : productList) {
            if (nameinput.equals(product.getP_name())) {
            	System.out.println("이미 등록");
                response.put("message", "이미 등록된 상품명입니다");

        	    return ResponseEntity.ok(response);
            }
        }
        try {
            ProductVO product = new ProductVO();
            product.setP_name(nameinput);
            product.setP_price(priceinput);
            Long quantityint = Long.parseLong(quantityinput);
            product.setP_quantity(quantityint);

            if (percentinput.isEmpty()) {
                product.setP_discount(null);
            } else {
                product.setP_discount(percentinput);
            }

            if (dateinputstart.isEmpty() && dateinputend.isEmpty()) {
                product.setP_discount_start(null);
                product.setP_discount_end(null);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date startDate = sdf.parse(dateinputstart);
	            java.util.Date endDate = sdf.parse(dateinputend);
	            // java.util.Date를 java.sql.Date로 변환
	            Date sqlStartDate = new Date(startDate.getTime());
	            Date sqlEndDate = new Date(endDate.getTime());
	            product.setP_discount_start(sqlStartDate);
	            product.setP_discount_end(sqlEndDate);
            }

            product.setP_category(categoryradio);
            
            String uniqueFileName = UUID.randomUUID().toString() + "-" + detailImageFile.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(detailImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            product.setP_detailpath("/display/"+uniqueFileName);
            
            ProductMapper.productinsert(product);

            ProductOptionVO productoption = new ProductOptionVO();
            if (optioninput != null && optiondetailinput != null) {
            	List<String> optioninputList = new ObjectMapper().readValue(optioninput, new TypeReference<List<String>>() {});
                List<String> optiondetailinputList = new ObjectMapper().readValue(optiondetailinput, new TypeReference<List<String>>() {});
                
                for (int i = 0; i < optioninputList.size(); i++) {
                    productoption.setPo_name(nameinput);
                    productoption.setPo_option(optioninputList.get(i));
                    productoption.setPo_optiondetail(optiondetailinputList.get(i));
                    ProductOptionMapper.optioninsert(productoption);
                }
            }
            else {
            	productoption.setPo_name(nameinput);
            	productoption.setPo_option(null);
            	productoption.setPo_optiondetail(null);
            	ProductOptionMapper.optioninsert(productoption);
            }
            
            ProductPathVO productpath = new ProductPathVO();
            for (MultipartFile file : imageFiles) {
                String uniqueFilesName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
                Path uploadPaths = Paths.get(UPLOAD_DIR);
                Path filesPath = uploadPaths.resolve(uniqueFilesName);
                Files.copy(file.getInputStream(), filesPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Uploaded file path: " + filesPath);
                productpath.setPp_name(nameinput);
                productpath.setPp_path("/display/"+uniqueFilesName);
                ProductPathMapper.pathinsert(productpath);
                System.out.println("정상처리");  
            }
            System.out.println("정상처리");
        } catch (IOException | ParseException e) {
        	response.put("message", "상품 등록 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "상품을 등록하였습니다");

	    return ResponseEntity.ok(response);
    }

	@GetMapping("/display/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> displayImage(@PathVariable String fileName) {
		System.out.println("display함수 들어옴");
        File file = new File(UPLOAD_DIR + "/" + fileName);

        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            Resource resource = new FileSystemResource(file);
            System.out.println(resource);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	//상품삭제
	@PostMapping(value = "/productdelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> productdelete(@RequestBody List<Map<String, String>> userData) {
		for (Map<String, String> userlist : userData) {
			String productname = userlist.get("productname");
		    //관리자 DB에 값 삭제
			ProductOptionMapper.optionnameDelete(productname);
			ProductPathMapper.pathnameDelete(productname);
			ProductMapper.productnameDelete(productname);
		    
		    System.out.println(productname + " 이 삭제되었습니다");
		}
	    return ResponseEntity.ok("상품이 삭제되었습니다");
	}
	
	//상품수정
	@GetMapping("/productupdate")
	public String productupdate(@RequestParam("name") String name, Model model, HttpSession session) {    
		System.out.println("수정 controller");
		ProductMapper.findByName(name);
		for (ProductVO productupdatelist : ProductMapper.findByName(name)) {
			model.addAttribute("productupdatelist", productupdatelist);
			session.setAttribute("clickproductlistnum", productupdatelist.getP_num());
			String path = productupdatelist.getP_detailpath();
			model.addAttribute("productdetailpath", path);
		}
		ProductOptionMapper.findByoptionName(name);
		session.setAttribute("clickproductoption", ProductOptionMapper.findByoptionName(name));
		List<String> optionlist = new ArrayList<>();
		List<String> optiondetaillist = new ArrayList<>();
		for (ProductOptionVO optionupdatelist : ProductOptionMapper.findByoptionName(name)) {
		    optionlist.add(optionupdatelist.getPo_option());
		    optiondetaillist.add(optionupdatelist.getPo_optiondetail());
		}
		model.addAttribute("optionlist", optionlist);
		model.addAttribute("optiondetaillist", optiondetaillist);
		ProductPathMapper.findBypathName(name);
		session.setAttribute("clickproductpath", ProductPathMapper.findBypathName(name));
		List<String> pathlist = new ArrayList<>();
		for (ProductPathVO pathupdatelist : ProductPathMapper.findBypathName(name)) {
			pathlist.add(pathupdatelist.getPp_path());
		}
		model.addAttribute("pathlist", pathlist);
		return "/admin/productupdate";
	}
	@PostMapping(value = "/productupdateprocess", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> productupdateprocess(@RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
    @RequestParam(value = "stringImageFiles", required = false) List<String> stringImageFiles, @RequestParam(value = "detailImageFile", required = false) MultipartFile detailImageFile,
    @RequestParam(value = "stringdetailImageFile", required = false) String stringdetailImageFile, @RequestParam("nameinput") String nameinput,
	@RequestParam("priceinput") String priceinput, @RequestParam("quantityinput") String quantityinput,
	@RequestParam("percentinput") String percentinput, @RequestParam("dateinputstart") String dateinputstart,
	@RequestParam("dateinputend") String dateinputend, @RequestParam("optioninput") String optioninput,
	@RequestParam("optiondetailinput") String optiondetailinput, @RequestParam("categoryradio") String categoryradio, HttpSession session) {
		System.out.println("수정프로세스 controller");
	    
	    List<ProductVO> productList = ProductMapper.productlist();
	    Long clickproductlistnum = (Long) session.getAttribute("clickproductlistnum");
	    //수정할 상품 리스트
    	ProductVO product = ProductMapper.getProductBynum(clickproductlistnum);
	    
		Map<String, String> response = new HashMap<>();
		
		//수정한 상품명이 상품리스트에 존재할 경우
		for (ProductVO productlist : productList) {
	        if (productlist.getP_name().equals(nameinput) && !product.getP_name().equals(nameinput)) {
	            System.out.println("이미 등록");
	            response.put("message", "이미 등록된 상품명입니다");
	            return ResponseEntity.ok(response);  
	        }
	    }

		try {
			//옵션 수정
			List<ProductOptionVO> clickproductoption = (List<ProductOptionVO>) session.getAttribute("clickproductoption");

		    List<String> optioninputList = new ObjectMapper().readValue(optioninput, new TypeReference<List<String>>() {});
		    List<String> optiondetailinputList = new ObjectMapper().readValue(optiondetailinput, new TypeReference<List<String>>() {});

		    if(!clickproductoption.isEmpty()) {
		    	if(clickproductoption.size()==optioninputList.size()) {
		    		for (int i = 0; i < clickproductoption.size(); i++) {
				        ProductOptionVO productOption = clickproductoption.get(i);
			            String option = optioninputList.get(i);
			            String optiondetail = optiondetailinputList.get(i);
			            
			            productOption.setPo_name(nameinput);
			            productOption.setPo_option(option);
			            productOption.setPo_optiondetail(optiondetail);
			            ProductOptionMapper.optionupdate(productOption);
				    }
		    	}
	    		//옵션 추가했을때
		    	else if(clickproductoption.size()<optioninputList.size()) {
		    		int optionInputListSize = optioninputList.size();
	    			int clickProductOptionSize = clickproductoption.size();
	    			
	    			for (int i = clickProductOptionSize; i < optionInputListSize; i++) {
	    				ProductOptionVO productoption = new ProductOptionVO();
	                	productoption.setPo_name(nameinput);
	                	productoption.setPo_option(optioninputList.get(i));
	                	productoption.setPo_optiondetail(optiondetailinputList.get(i));
	                	ProductOptionMapper.optioninsert(productoption);
	    			}
		    	}
		    	//옵션 제거했을때
		    	else if(clickproductoption.size()>optioninputList.size()) {
	    			int clickProductOptionSize = clickproductoption.size();
	    			
		    		for (int i = 0; i < clickProductOptionSize; i++) {
		    			ProductOptionVO productOption = clickproductoption.get(i);
		    			String optiondetail = productOption.getPo_optiondetail();
		    			if(!optiondetailinputList.contains(optiondetail)) {
		    				ProductOptionMapper.optionnumDelete(productOption.getPo_num());
		    			}
	    			}
		    	}
		    } else {
		    	ProductOptionVO productoption = new ProductOptionVO();
	            if (optioninput != null && optiondetailinput != null) {     
	                for (int i = 0; i < optioninputList.size(); i++) {
	                    productoption.setPo_name(nameinput);
	                    productoption.setPo_option(optioninputList.get(i));
	                    productoption.setPo_optiondetail(optiondetailinputList.get(i));
	                    ProductOptionMapper.optioninsert(productoption);
	                }
	            }
		    }
		    System.out.println("옵션수정 완료");
		    
		    //메인이미지 수정
		    List<ProductPathVO> clickproductpath = (List<ProductPathVO>) session.getAttribute("clickproductpath");
			
			if(!imageFiles.isEmpty()) {
		    	for (ProductPathVO productpath : clickproductpath) {
			    	ProductPathMapper.pathnameDelete(productpath.getPp_name());
			    }
				for (MultipartFile file : imageFiles) {
	    			ProductPathVO productpath = new ProductPathVO();
	                String uniqueFilesName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
	                Path uploadPaths = Paths.get(UPLOAD_DIR);
	                Path filesPath = uploadPaths.resolve(uniqueFilesName);
	                Files.copy(file.getInputStream(), filesPath, StandardCopyOption.REPLACE_EXISTING);
	                System.out.println("Uploaded file path: " + filesPath);
	                productpath.setPp_name(nameinput);
	                productpath.setPp_path("/display/"+uniqueFilesName);
	                ProductPathMapper.pathinsert(productpath);
	            }
			}
			else if(stringImageFiles!=null) {
				if (clickproductpath.size() != stringImageFiles.size()) {
			    	for (ProductPathVO productpath : clickproductpath) {
				    	ProductPathMapper.pathnameDelete(productpath.getPp_name());
				    }
					for (String file : stringImageFiles) {
		    			ProductPathVO productpath = new ProductPathVO();
		    			productpath.setPp_name(nameinput);
			    		productpath.setPp_path(file);
			    		ProductPathMapper.pathinsert(productpath);
		    		}
				}
				else {
					for (int i = 0; i < clickproductpath.size(); i++) {
	            		ProductPathVO productPath = clickproductpath.get(i);
	            		productPath.setPp_name(nameinput);
	            		productPath.setPp_path(productPath.getPp_path());
	            		
	            		ProductPathMapper.pathupdate(productPath);
	            	}
				}
			}
		    System.out.println("이미지수정 완료");
		    
		    //상품 수정
        	product.setP_name(nameinput);
        	product.setP_price(priceinput);
        	Long quantityint = Long.parseLong(quantityinput);
        	product.setP_quantity(quantityint);

        	if (percentinput.isEmpty()) {
        		product.setP_discount(null);
	        } else {
	            product.setP_discount(percentinput);
	        }
            if (dateinputstart.isEmpty() && dateinputend.isEmpty()) {
                product.setP_discount_start(null);
                product.setP_discount_end(null);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date startDate = sdf.parse(dateinputstart);
	            java.util.Date endDate = sdf.parse(dateinputend);
	            // java.util.Date를 java.sql.Date로 변환
	            Date sqlStartDate = new Date(startDate.getTime());
	            Date sqlEndDate = new Date(endDate.getTime());
	            product.setP_discount_start(sqlStartDate);
	            product.setP_discount_end(sqlEndDate);
            }
            product.setP_category(categoryradio);    
            System.out.println("detailImageFile: "+detailImageFile);
            if (detailImageFile!=null) {
            	String uniqueFileName = UUID.randomUUID().toString() + "-" + detailImageFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.copy(detailImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                product.setP_detailpath("/display/"+uniqueFileName);
            }
            else {
            	System.out.println("stringdetailImageFile: "+stringdetailImageFile);
            	product.setP_detailpath(stringdetailImageFile);
            }   
            ProductMapper.productupdate(product);
            System.out.println("ProductMapper 수정");
        } catch (Exception e) {
        	response.put("message", "상품 수정 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
	    response.put("message", "상품을 수정하였습니다");

	    return ResponseEntity.ok(response);
    };
	
	
	//검색
	@GetMapping("/productsearch")
	public String productsearch(@RequestParam("topic") String topic, @RequestParam("keyword") String keyword, HttpSession session) {
		System.out.println(topic);
		System.out.println(keyword);
		
		List<ProductVO> list = ProductMapper.productlist();
		List<ProductVO> searchList = new ArrayList<>();
		
		//키워드가 해당 주제에 포함되어있으면 List에 추가
		for (ProductVO product:list) {
			switch (topic) {
				case "name":
					if (keyword.equals(product.getP_name())) {
						searchList.add(product);
					}
					break;
				 case "price":
					if (keyword.equals(product.getP_price())) {
						searchList.add(product);
					}
					break;
				 case "discount":
					 if (keyword.equals(product.getP_discount()) || keyword.equals(product.getP_discount()+"%")) {
						 searchList.add(product);
					 }
					 break;
				 case "category":
					 if (keyword.equals(product.getP_category())) {
						 searchList.add(product);
					 }
					 break;
				 default:
		                System.out.println("결과없음");
		            break;
			}
		}
		session.setAttribute("product_searchList", searchList);
		
		return "/admin/productList";
	}
}
