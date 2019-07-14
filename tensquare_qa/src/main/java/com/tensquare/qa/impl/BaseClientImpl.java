package com.tensquare.qa.impl;

import com.common.entity.Result;
import com.common.entity.StatusCode;
import com.tensquare.qa.client.BaseClient;
import org.springframework.stereotype.Component;

@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"触发熔断器");
    }
}
