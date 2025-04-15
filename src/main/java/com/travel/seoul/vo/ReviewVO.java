package com.travel.seoul.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewVO {
	public long r_num;
	public long m_num;
	public long p_num;
	public Long po_num;
	public long r_countstar;
	public String r_reviewcontent;
	public Date r_date;
}
