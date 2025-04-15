package com.travel.seoul.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class OrderVO {
	public long o_num;
	public long m_num;
	public long p_num;
	public Long c_num;
	public long o_quantity;
	public String o_price;
	public String o_paymentstatus;
	public String o_deliverystatus;
	public String o_reviewstatus;
	public String o_totalprice;
	public String o_option;
	public String o_optiondetail;
	public String o_optionprice;
	public Date o_date;
	public String o_image;
	public String o_number;
}
