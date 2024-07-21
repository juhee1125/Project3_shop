package com.travel.seoul.vo;
  
import java.sql.Date;
import java.util.List;

import lombok.Data;
  
@Data 
public class ProductVO { 
	private long p_num; 
	public String p_name; 
	private String p_price; 
	public long p_quantity; 
	private String p_detailpath; 
	private String p_discount; 
	private Date p_discount_start; 
	private Date p_discount_end; 
	private String p_category;
}

  