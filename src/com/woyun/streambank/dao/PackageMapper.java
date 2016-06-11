package com.woyun.streambank.dao;

import com.woyun.streambank.model.NumberSection;



@MyBatisRepository
public interface PackageMapper {
	public NumberSection getPhoneOperator(String section);
} 
