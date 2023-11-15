package com.hthk.datacenter.service;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.fintech.model.trade.datacenter.IDataCriteriaTrade;

import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 12:12
 */
public interface TradeService<T extends IDataCriteriaTrade> {

    List<ITrade> get(T criteria);

    List<ITrade> upsert();

}
