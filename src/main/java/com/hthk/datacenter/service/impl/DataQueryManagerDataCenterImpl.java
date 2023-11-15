package com.hthk.datacenter.service.impl;

import com.hthk.fintech.model.data.datacenter.query.IDataCriteria;
import com.hthk.fintech.model.web.http.HttpRequest;
import com.hthk.fintech.service.DataQueryManagerService;
import org.springframework.stereotype.Service;

/**
 * @Author: Rock CHEN
 * @Date: 2023/11/15 13:42
 */
@Service
public class DataQueryManagerDataCenterImpl implements DataQueryManagerService {

    @Override
    public <R> R process(HttpRequest<? extends IDataCriteria> request) {
        return null;
    }

}
