package com.hthk.datacenter.service.impl;

import com.hthk.calypsox.model.trade.ITrade;
import com.hthk.calypsox.model.trade.datacenter.DataCriteriaTrade;
import com.hthk.common.utils.CSVFileUtils;
import com.hthk.common.utils.FileUtils;
import com.hthk.datacenter.converter.DummyConverter;
import com.hthk.datacenter.service.TradeService;
import com.hthk.fintech.collection.ComparatorFileNameLastDateASC;
import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.converter.IConverter;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.SnapshotImageEnum;
import com.hthk.fintech.model.data.datacenter.service.DataCenterService;
import com.hthk.fintech.model.trade.dto.TradeCSVDTO;
import com.hthk.fintech.service.DataQueryManagerService;
import com.hthk.fintech.service.basic.AbstractService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.hthk.fintech.config.DataCenterStaticData.*;
import static com.hthk.fintech.config.FintechStaticData.*;
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

    private ComparatorFileNameLastDateASC comparator = new ComparatorFileNameLastDateASC();

    @Override
    public List<ITrade> get(DataSnapshot snapshot, DataCriteriaTrade criteria) throws ServiceNotSupportedException, ServiceInternalException {

        checkIfSupported(snapshot);
        File tradeSrcFolder = getTradeSrcFolder(appConfig, KW_FOLDER_NAME_TRADE);
        logger.info(LOG_DEFAULT, "src folder", tradeSrcFolder);

        String fileNamePrefix = getFileNamePrefix(KW_DATACENTER_FILE_NAME_PREFIX, KW_FILE_NAME_TRADE);
        logger.info(LOG_DEFAULT, "fileNamePrefix", fileNamePrefix);

        List<File> srcFileList = getSrcFileList(tradeSrcFolder, fileNamePrefix);
        log(srcFileList);

        List<ITrade> iTradeList = null;
        try {
            iTradeList = loadTrade(srcFileList);
        } catch (IOException e) {
            throw new ServiceInternalException(e.getMessage(), e);
        }
        logger.info(LOG_DEFAULT, "trade count", iTradeList.size());
        return iTradeList;
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

    private List<ITrade> loadTrade(List<File> sortedByDateList) throws IOException {

        Map<String, ITrade> allTradeMap = new HashedMap();
        Map<LocalDate, List<ITrade>> tradeMap = new HashMap<>();
        for (int i = 0; i < sortedByDateList.size(); i++) {
            File file = sortedByDateList.get(i);
            LocalDate date = FileUtils.getFileDate(file, BASIC_DATE_FORMAT);
            tradeMap.put(date, loadTrade(file));
        }

        List<LocalDate> dateList = tradeMap.keySet().stream().collect(Collectors.toList());
        Collections.sort(dateList);

        dateList.forEach(d -> {
            List<ITrade> tradeList = tradeMap.get(d);
            tradeList.forEach(t -> {
                String tradeId = t.getId();
                allTradeMap.put(tradeId, t);
            });
        });
        return allTradeMap.values().stream().collect(Collectors.toList());
    }

    private List<ITrade> loadTrade(File file) throws IOException {

        List<TradeCSVDTO> tradeCSVList = CSVFileUtils.readCSV(file.getAbsolutePath(), TradeCSVDTO.class);
        logger.info("{}", tradeCSVList.size());
        return tradeCSVList.stream().map(t -> getConverter(t).process(t)).collect(Collectors.toList());
    }

    private IConverter<TradeCSVDTO, ? extends ITrade> getConverter(TradeCSVDTO t) {
        return new DummyConverter();
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

    private List<File> getSrcFileList(File tradeSrcFolder, String fileNamePrefix) {

        List<String> subFileList = FileUtils.getAllSubFileList(tradeSrcFolder);
        List<String> filterList = filterNoDate(subFileList);
        return filterList.stream().map(t -> new File(t)).filter(t -> t.getName().startsWith(fileNamePrefix)).collect(Collectors.toList());
    }

    private List<String> filterNoDate(List<String> subFileList) {
        return subFileList.stream().filter(t -> {
            String fileName = new File(t).getName();
            String simFileName = fileName.substring(0, fileName.indexOf("."));
            String date = simFileName.substring(simFileName.length() - 8);
            try {
                LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
                return true;
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
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

    private String getFileNamePrefix(String kwDatacenterFileNamePrefix, String kwFolderNameTrade) {
        return kwDatacenterFileNamePrefix + "_" + kwFolderNameTrade;
    }

    private void log(List<File> srcFileList) {
        if (CollectionUtils.isEmpty(srcFileList)) {
            return;
        }
        String log = srcFileList.stream().map(t -> t.getName()).collect(Collectors.joining("\r\n"));
        logger.info(LOG_WRAP, "src file list", log);
    }

    private List<File> sort(List<File> srcFileList) {

        Collections.sort(srcFileList, comparator);
        return srcFileList;
    }

}