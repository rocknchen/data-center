package com.hthk.datacenter.converter;

import com.hthk.calypsox.model.trade.fx.FXForward;
import com.hthk.fintech.converter.IConverter;
import com.hthk.fintech.model.trade.dto.TradeCSVDTO;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 19:44
 * TODO delete
 */
@Deprecated
public class DummyConverter implements IConverter<TradeCSVDTO, FXForward> {

    @Override
    public FXForward process(TradeCSVDTO entity) {

        FXForward trade = new FXForward();
        trade.setId(entity.getTradeId());
        return trade;
    }

}
