package com.travel.seoul.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class QnAVO {
	public long q_num;
	public String q_id;
	public String q_title;
	public String q_content;
	public Date q_date;
	public String q_answerstatus;
}
