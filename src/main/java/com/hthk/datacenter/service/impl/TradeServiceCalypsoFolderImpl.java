package com.hthk.datacenter.service.impl;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.calypsox.model.trade.datacenter.DataCriteriaTrade;
import com.hthk.datacenter.service.TradeService;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.SnapshotImageEnum;
import com.hthk.fintech.model.data.datacenter.service.DataCenterService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.hthk.fintech.config.DataCenterStaticData.WHITE_LIST_FILE_SUPPORTED_IMAGE_TYPE;
import static com.hthk.fintech.model.data.datacenter.query.EntityTypeEnum.TRADE;
import static com.hthk.fintech.model.software.app.ApplicationEnum.CALYPSO;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 13:26
 */
@DataCenterService(sourceName = CALYPSO, sourceType = DataSourceTypeEnum.FOLDER, entityType = TRADE)
@Service("tradeServiceCalypsoFolder")
public class TradeServiceCalypsoFolderImpl implements TradeService {

    @Override
    public List<ITrade> get(DataSnapshot snapshot, DataCriteriaTrade criteria) throws ServiceNotSupportedException {

        checkIfSupported(snapshot);
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

    private void checkIfSupported(DataSnapshot snapshot) throws ServiceNotSupportedException {
        SnapshotImageEnum image = snapshot.getImage();
        boolean isSupported = WHITE_LIST_FILE_SUPPORTED_IMAGE_TYPE.contains(image);
        boolean notSupported = !isSupported;
        if (notSupported) {
            String errMsg = String.format("Calypso Trade using File as DataCenter, not support %s. Supported white list %s", image, WHITE_LIST_FILE_SUPPORTED_IMAGE_TYPE);
            throw new ServiceNotSupportedException(errMsg);
        }
    }

}