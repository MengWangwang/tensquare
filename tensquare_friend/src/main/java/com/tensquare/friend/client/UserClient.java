package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient(value = "tensquare-user")
public interface UserClient {

    @PutMapping(value = "/user/{userid}/{friendid}/{x}")
    public void updateFanscountAndFollowcount( @PathVariable("userid") String userid, @PathVariable("friendid") String friendid,@PathVariable("x") int x);
}
