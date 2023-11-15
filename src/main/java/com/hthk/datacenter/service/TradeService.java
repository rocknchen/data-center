package com.hthk.datacenter.service;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.trade.datacenter.IDataCriteriaTrade;
import com.hthk.fintech.service.DataQueryService;

import java.io.File;
import java.util.List;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 12:12
 */
public interface TradeService<T extends IDataCriteriaTrade> extends DataQueryService {

    List<ITrade> upsert(DataSnapshot snapshot, File srcFolder);

    List<ITrade> upsert(DataSnapshot snapshot, List<ITrade> tradeList);

}
