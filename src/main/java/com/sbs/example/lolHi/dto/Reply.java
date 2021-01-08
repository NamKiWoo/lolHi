package com.sbs.example.lolHi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	
	private int id;
	private String regDate;
	private String updateDate;
	private String relTypeCode;
	private String relId;
	private String body;
	private int memberId;
}
