package com.woyun.streambank.service;

import com.woyun.streambank.model.Dealer;

/**
 * 代理商业务
 * @author 芮浩
 * @date 2016-5-30
 *
 */
public interface DealerService {
	public Dealer  findDealerByNo(String dealerNo);
}
