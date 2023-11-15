package com.hthk.datacenter.service.impl;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.calypsox.model.trade.datacenter.DataCriteriaTrade;
import com.hthk.datacenter.service.TradeService;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.service.DataCenterService;
import com.hthk.fintech.model.finance.Trade;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.hthk.fintech.model.data.datacenter.query.EntityTypeEnum.TRADE;
import static com.hthk.fintech.model.software.app.ApplicationEnum.CALYPSO;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 21:59
 */
@DataCenterService(sourceName = CALYPSO, sourceType = DataSourceTypeEnum.DATABASE_ORACLE, entityType = TRADE)
@Service("tradeServiceCalypsoDBOracle")
public class TradeServiceCalypsoDBOracleImpl implements TradeService {

    @Override
    public List<ITrade> get(DataSnapshot snapshot, DataCriteriaTrade criteria) {

        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();
        List<Trade> list = new ArrayList<>();
        list.add(trade1);
        list.add(trade2);
        list.add(trade3);
        return null;
    }

    /**
     * TODO
     */
    @Override
    public List<ITrade> upsert(DataSnapshot snapshot, File srcFolder) {
        return null;
    }

    /**
     * TODO
     */
    @Override
    public List<ITrade> upsert(DataSnapshot snapshot, List<ITrade> tradeList) {
        return null;
    }


}
