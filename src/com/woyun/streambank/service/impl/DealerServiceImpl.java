package com.woyun.streambank.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woyun.streambank.dao.DealerMapper;
import com.woyun.streambank.model.Dealer;
import com.woyun.streambank.service.DealerService;

@Service("dealerService")
public class DealerServiceImpl implements DealerService{
	
	@Autowired
	private DealerMapper dealerMapper;

	public Dealer findDealerByName(String dealerNo) {
		return dealerMapper.findDealerByName(dealerNo);
	}

}
