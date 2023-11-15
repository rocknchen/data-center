package com.hthk.datacenter.service.impl;

import com.hthk.datacenter.model.query.TradeCriteriaCalypso;
import com.hthk.datacenter.service.TradeServiceCalypso;
import com.hthk.fintech.model.data.datacenter.query.DataCriteria;
import com.hthk.fintech.model.finance.Trade;
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
public class TradeServiceCalypsoImpl implements TradeServiceCalypso {

    @Override
    public List<Trade> get(TradeCriteriaCalypso criteria) {

        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();
        List<Trade> list = new ArrayList<>();
        list.add(trade1);
        list.add(trade2);
        list.add(trade3);
        return list;
    }

}
