package com.hthk.datacenter.service.impl;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.calypsox.model.trade.datacenter.DataCriteriaTrade;
import com.hthk.datacenter.service.TradeService;
import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.SnapshotImageEnum;
import com.hthk.fintech.model.data.datacenter.service.DataCenterService;
import com.hthk.fintech.service.basic.AbstractService;
import com.hthk.fintech.web.http.exp.DefaultAppExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import static com.hthk.fintech.config.DataCenterStaticData.KW_FOLDER_NAME_TRADE;
import static com.hthk.fintech.config.DataCenterStaticData.WHITE_LIST_FILE_SUPPORTED_IMAGE_TYPE;
import static com.hthk.fintech.config.FintechStaticData.LOG_DEFAULT;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;
import static com.hthk.fintech.model.data.datacenter.query.EntityTypeEnum.TRADE;
import static com.hthk.fintech.model.software.app.ApplicationEnum.CALYPSO;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 13:26
 */
@DataCenterService(sourceName = CALYPSO, sourceType = DataSourceTypeEnum.FOLDER, entityType = TRADE)
@Service("tradeServiceCalypsoFolder")
public class TradeServiceCalypsoFolderImpl extends AbstractService implements TradeService {

    private static final Logger logger = LoggerFactory.getLogger(TradeServiceCalypsoFolderImpl.class);

    @Override
    public List<ITrade> get(DataSnapshot snapshot, DataCriteriaTrade criteria) throws ServiceNotSupportedException, ServiceInternalException {

        checkIfSupported(snapshot);
        File tradeSrcFolder = getTradeSrcFolder(appConfig, KW_FOLDER_NAME_TRADE);
        logger.info(LOG_DEFAULT, "src folder", tradeSrcFolder);

        List<File> srcFileList = getSrcFileList(tradeSrcFolder);
        logger.info(LOG_WRAP, "src file list", srcFileList);

        return null;

    }

    private List<File> getSrcFileList(File tradeSrcFolder) {
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

    private File getTradeSrcFolder(AppConfig appConfig, String kwFolderNameTrade) throws ServiceInternalException {

        File file = new File(appConfig.getDataSourcePath() + "/" + kwFolderNameTrade);
        boolean isExist = file.exists();
        boolean isFolder = file.isDirectory();
        boolean isSrcFolderExist = isExist && isFolder;
        if (!isSrcFolderExist) {
            throw new ServiceInternalException("Calypso Trade src folder missing");
        }
        return file;
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