package com.hthk.datacenter.service;

import com.hthk.datacenter.model.query.TradeCriteriaCalypso;
import com.hthk.fintech.model.finance.Trade;

import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 21:59
 */
public interface TradeServiceCalypso {

    List<Trade> get(TradeCriteriaCalypso criteria);

}
