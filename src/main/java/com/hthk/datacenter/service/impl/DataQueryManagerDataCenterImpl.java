package com.hthk.datacenter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.exception.ServiceException;
import com.hthk.fintech.exception.ServiceInvalidException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.DataSourceFolder;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.IDataSource;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.serialize.HttpRequestDeserializer;
import com.hthk.fintech.service.DataQueryManagerService;
import com.hthk.fintech.service.basic.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hthk.fintech.config.FintechStaticData.KW_HTTP_REQUEST;
import static com.hthk.fintech.config.FintechStaticData.LOG_WRAP;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 13:42
 */
@Service
public class DataQueryManagerDataCenterImpl extends AbstractService

        implements DataQueryManagerService {

    private final static Logger logger = LoggerFactory.getLogger(DataQueryManagerDataCenterImpl.class);

    @Override
    public <R> R process(HttpRequest<? extends IDataCriteria> request) throws ServiceInvalidException {

        IDataSource dataSource = getSource(appConfig, request);
        try {
            logger.info(LOG_WRAP, "dataSource", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataSource));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        logger.info("dataSource: {}", dataSource);
//        DataService service = getService(dataSource);
//        Object result = service.get(dataSource, request);
//        return result;
        return null;
    }

    private IDataSource getSource(
            AppConfig appConfig,
            HttpRequest<? extends IDataCriteria> request
    ) throws ServiceInvalidException {

        DataSourceTypeEnum dataSourceType = appConfig.getDataSourceType();
        switch (dataSourceType) {
            case FOLDER:
                String srcPath = appConfig.getDataSourcePath();
                return new DataSourceFolder(srcPath);
            default:
                throw new ServiceInvalidException();
        }
    }

}
