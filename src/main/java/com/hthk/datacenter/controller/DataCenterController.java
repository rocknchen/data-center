package com.hthk.datacenter.controller;

import com.hthk.fintech.component.AbstractComponent;
import com.hthk.fintech.model.web.http.HttpResponse;
import com.hthk.fintech.service.AppInfoService;
import com.hthk.fintech.utils.HttpResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @PostMapping(value = "/")
    public HttpResponse<?> post() {
        logger.info("{}", "post");
        return HttpResponseUtils.success();
    }

}
