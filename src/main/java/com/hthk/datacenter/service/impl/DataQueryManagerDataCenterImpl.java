package com.hthk.datacenter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.exception.ServiceException;
import com.hthk.fintech.exception.ServiceInvalidException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.DataSourceFolder;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.IDataSource;
import com.hthk.fintech.model.data.datacenter.query.DataQueryRequest;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.EntityCriteria;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.serialize.HttpRequestDeserializer;
import com.hthk.fintech.service.DataQueryManagerService;
import com.hthk.fintech.service.basic.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.hthk.fintech.config.FintechStaticData.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 13:42
 */
@Service
public class DataQueryManagerDataCenterImpl extends AbstractService

        implements DataQueryManagerService {

    private final static Logger logger = LoggerFactory.getLogger(DataQueryManagerDataCenterImpl.class);

    @Override
    public <R> R process(HttpRequest<? extends IDataCriteria> request) throws ServiceInvalidException, JsonProcessingException {

        IDataSource dataSource = getSource(appConfig, request);
        logger.info(LOG_WRAP, "dataSource", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataSource));

        DataSourceTypeEnum dsType = dataSource.getType();
        DataQueryRequest dataQueryRequest = request.getData();
        EntityCriteria entityCriteria = dataQueryRequest.getEntity();
//        DataService service = getService(dsType, entityCriteria);

        DataSnapshot snapshot = dataQueryRequest.getSnapshot();
        IDataCriteria dataCriteria = dataQueryRequest.getCriteria();
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
                check(srcPath);
                return new DataSourceFolder(srcPath);
            default:
                throw new ServiceInvalidException();
        }
    }

    private void check(String srcPath) throws ServiceInvalidException {
        if (!new File(srcPath).exists()) {
            throw new ServiceInvalidException("src path not exists: " + srcPath);
        }
    }

}
