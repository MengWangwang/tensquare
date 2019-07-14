package com.tensquare.qa.client;

import com.common.entity.Result;
import com.tensquare.qa.impl.BaseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    @GetMapping(value = "/label/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId);
}
