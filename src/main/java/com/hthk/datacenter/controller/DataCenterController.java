package com.hthk.datacenter.controller;

import com.hthk.fintech.component.AbstractComponent;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.model.web.http.HttpResponse;
import com.hthk.fintech.utils.HttpResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 14:05
 */
@RestController
@RequestMapping("/")
public class DataCenterController extends AbstractComponent {

    private final static Logger logger = LoggerFactory.getLogger(DataCenterController.class);

    @GetMapping(value = "/appVersion")
    public HttpResponse<?> getAppVersion() {
        logger.info("{}", "appVersion");
        return HttpResponseUtils.success(appInfoService.getVersion());
    }

    @PostMapping(value = "/data")
    public HttpResponse<?> post(@RequestBody HttpRequest request) {
        logger.info("{}", "post");
        return HttpResponseUtils.success();
    }

}
