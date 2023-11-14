package com.hthk.datacenter.controller;

import com.hthk.fintech.model.web.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/14 14:05
 */
@RestController
@RequestMapping("/")
public class DataCenterController {

    private final static Logger logger = LoggerFactory.getLogger(DataCenterController.class);

    @PostMapping(value = "/")
    public HttpResponse<?> post(
    ) {
        return null;
    }

}
