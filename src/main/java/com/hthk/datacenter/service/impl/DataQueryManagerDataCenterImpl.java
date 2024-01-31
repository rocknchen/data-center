package com.hthk.datacenter.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.fintech.config.AppConfig;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.exception.ServiceInvalidException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.DataSourceDBOracle;
import com.hthk.fintech.model.data.DataSourceFolder;
import com.hthk.fintech.model.data.DataSourceTypeEnum;
import com.hthk.fintech.model.data.IDataSource;
import com.hthk.fintech.model.data.datacenter.query.DataQueryRequest;
import com.hthk.fintech.model.data.datacenter.query.DataSnapshot;
import com.hthk.fintech.model.data.datacenter.query.EntityCriteria;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.data.datacenter.service.DataCenterService;
import com.hthk.fintech.model.software.app.Application;
import com.hthk.fintech.model.software.app.ApplicationEnum;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.model.web.http.HttpServiceRequest;
import com.hthk.fintech.service.DataQueryManagerService;
import com.hthk.fintech.service.DataQueryService;
import com.hthk.fintech.service.basic.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public <R> R process(HttpRequest<? extends IDataCriteria> request) throws ServiceInvalidException, JsonProcessingException, ServiceNotSupportedException, ServiceInternalException {

        IDataSource dataSource = getSource(appConfig, request);
        logger.info(LOG_WRAP, "dataSource", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataSource));

        Application app = null;// request.getSource();
        ApplicationEnum appName = app.getName();

        DataSourceTypeEnum dsType = dataSource.getType();
        DataQueryRequest dataQueryRequest = null;// request.getData();
        EntityCriteria entityCriteria = dataQueryRequest.getEntity();
        DataQueryService dqService = getService(appName, dsType, entityCriteria);
        logger.info(LOG_DEFAULT, "dataQueryService", dqService.getClass().getAnnotation(Service.class).value());

        DataSnapshot snapshot = dataQueryRequest.getSnapshot();
        IDataCriteria dataCriteria = dataQueryRequest.getCriteria();
        return (R) dqService.get(snapshot, dataCriteria);
    }

    private DataQueryService getService(ApplicationEnum appName, DataSourceTypeEnum dsType, EntityCriteria entityCriteria) {

        Map<String, DataQueryService> map = appContext.getBeansOfType(DataQueryService.class);
        List<DataQueryService> serviceList = map.values().stream().collect(Collectors.toList());
        List<DataQueryService> activeServiceList = serviceList.stream()
                .filter(t -> t.getClass().getAnnotation(DataCenterService.class) != null).collect(Collectors.toList());
        List<DataQueryService> validServiceList = activeServiceList.stream().filter(t -> isAccept(t, appName, dsType, entityCriteria)).collect(Collectors.toList());
        return validServiceList.get(0);
    }

    private boolean isAccept(DataQueryService service, ApplicationEnum appName, DataSourceTypeEnum dsType, EntityCriteria entityCriteria) {
        DataCenterService anno = service.getClass().getAnnotation(DataCenterService.class);
        boolean isAccept =
                anno.sourceName().equals(appName)
                        && anno.sourceType().equals(dsType)
                        && anno.entityType().equals(entityCriteria.getType());
        return isAccept;
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
            case DATABASE_ORACLE:
                return new DataSourceDBOracle();
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
