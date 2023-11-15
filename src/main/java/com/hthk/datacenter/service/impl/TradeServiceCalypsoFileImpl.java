package com.hthk.datacenter.service.impl;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.calypsox.model.trade.datacenter.DataCriteriaTrade;
import com.hthk.datacenter.service.TradeService;
import com.hthk.fintech.model.data.datacenter.query.DataCriteria;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.finance.Trade;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.hthk.fintech.model.data.datacenter.query.EntityTypeEnum.TRADE;
import static com.hthk.fintech.model.software.app.ApplicationEnum.CALYPSO;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 13:26
 */
@DataCriteria(sourceName = CALYPSO, entityType = TRADE)
@Service("tradeServiceCalypsoFile")
public class TradeServiceCalypsoFileImpl implements TradeService<DataCriteriaTrade> {

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

    @Override
    public Object get(DataSnapshot snapshot, IDataCriteria criteria) {
        return null;
    }

}