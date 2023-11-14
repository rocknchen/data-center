package com.hthk.datacenter.model.query.basic;

import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;

import java.time.LocalDate;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 19:39
 */
public abstract class AbstractTradeCriteria implements IDataCriteria {

    protected LocalDate tradeDate;

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }
}
