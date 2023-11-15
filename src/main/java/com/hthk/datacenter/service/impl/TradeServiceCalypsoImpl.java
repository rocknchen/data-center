package com.hthk.datacenter.service.impl;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.calypsox.model.trade.datacenter.DataCriteriaTrade;
import com.hthk.datacenter.service.TradeService;
import com.hthk.fintech.model.data.datacenter.query.DataCriteria;
import com.hthk.fintech.model.finance.Trade;
import com.hthk.fintech.model.trade.datacenter.IDataCriteriaTrade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.hthk.fintech.model.data.datacenter.query.EntityTypeEnum.TRADE;
import static com.hthk.fintech.model.software.app.ApplicationEnum.CALYPSO;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 21:59
 */
@DataCriteria(sourceName = CALYPSO, entityType = TRADE)
@Service
public class TradeServiceCalypsoImpl implements TradeService<DataCriteriaTrade> {

    @Override
    public List<ITrade> get(DataCriteriaTrade criteria) {

        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();
        List<Trade> list = new ArrayList<>();
        list.add(trade1);
        list.add(trade2);
        list.add(trade3);
        return null;
    }

    @Override
    public List<ITrade> upsert() {
        return null;
    }

}
