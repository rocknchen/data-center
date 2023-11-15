package com.hthk.datacenter.converter;

import com.hthk.calypsox.model.trade.fx.FXForward;
import com.hthk.fintech.converter.IConverter;
import com.hthk.fintech.model.trade.dto.TradeCSVDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.hthk.fintech.config.FintechStaticData.DEFAULT_DATE_TIME_FORMAT;

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
        trade.setExternalReference(entity.getExternalReference());
        trade.setInternalReference(entity.getInternalReference());

        trade.setBook(entity.getBookName());
        trade.setBuySell(entity.getBuySell());
        trade.setCounterParty(entity.getCounterParty());

        trade.setProductType(entity.getProductType());
        trade.setProductSubtype(entity.getProductSubType());

        trade.setTradeDateTime(LocalDateTime.parse(entity.getTradeDateTime(), DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));

        return trade;
    }

}
