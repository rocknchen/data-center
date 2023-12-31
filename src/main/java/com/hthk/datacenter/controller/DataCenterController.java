package com.hthk.datacenter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hthk.fintech.component.AbstractComponent;
import com.hthk.fintech.exception.ServiceInternalException;
import com.hthk.fintech.exception.ServiceInvalidException;
import com.hthk.fintech.exception.ServiceNotSupportedException;
import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.software.app.AppVersion;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.model.web.http.HttpResponse;
import com.hthk.fintech.utils.HttpResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.hthk.fintech.config.FintechStaticData.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 14:05
 */
@RestController
@RequestMapping("/")
public class DataCenterController extends AbstractComponent {

    private final static Logger logger = LoggerFactory.getLogger(DataCenterController.class);

    @GetMapping(value = "/appVersion")
    public HttpResponse<?> getAppVersion() throws JsonProcessingException {

        AppVersion appVersion = appInfoService.getVersion();
        logger.info(LOG_WRAP, KW_APP_VERSION, getDefaultObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(appVersion));
        return HttpResponseUtils.success(appVersion);
    }

    @PostMapping(value = "/data")
    public <T extends IDataCriteria, R> HttpResponse<?> post(
            @RequestBody HttpRequest<T> request
    ) throws JsonProcessingException, ServiceInvalidException, ServiceNotSupportedException, ServiceInternalException {

        logger.info(LOG_WRAP, KW_HTTP_REQUEST, getDefaultObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
        R result = dqmService.process(request);
        return HttpResponseUtils.success(result);
    }

}
