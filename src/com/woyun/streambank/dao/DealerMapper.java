package com.woyun.streambank.dao;

import com.woyun.streambank.model.Dealer;



@MyBatisRepository
public interface DealerMapper {
//	public Dealer findDealerByNo(String dealerNo);

	public Dealer findDealerByName(String dealerName);
} 
