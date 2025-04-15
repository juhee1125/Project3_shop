package com.travel.seoul.vo;
  
import java.sql.Date;

import lombok.Data;
  
@Data 
public class CouponVO { 
	public long c_num; 
	public Long p_num; 
	public String c_name; 
	public String c_type; 
	public Long c_count; 
	public String c_discount_setting; 
	public String c_discount; 
	public String c_price; 
	public String c_discount_price; 
	public Date c_discount_start; 
	public Date c_discount_end; 
	public Date c_date; 
	public boolean c_useYN;
	}

  