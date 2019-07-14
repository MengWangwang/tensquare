package com.tensquare.friend.controller;

import com.common.entity.Result;
import com.common.entity.StatusCode;
import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
            //验证是否登录，是否拿到当前用户Id
          Claims claims= (Claims) request.getAttribute("claims_user");
          if(claims==null){
              return new Result(false,StatusCode.LOGINERROR,"权限不足");
          }

          //得到当前登录用户ID
       String userid= claims.getId();
        //判断是添加好友还是添加非好友
        if(type!=null){
            if(type.equals("1")){

               int flag= friendService.addFriend(userid,friendid);

               if(flag==0){
                   return new Result(false, StatusCode.ERROR,"不能重复添加");
               }
                if(flag==1) {
                    userClient.updateFanscountAndFollowcount(userid,friendid,1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }else if(type.equals("2")){

                int flag= friendService.addNoFriend(userid,friendid);

                if(flag==0){
                    return new Result(false, StatusCode.ERROR,"不能重复添加");
                }
                if(flag==1) {
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false, StatusCode.ERROR,"参数异常");
        }
    }
    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){
        //验证是否登录，是否拿到当前用户Id
        Claims claims= (Claims) request.getAttribute("claims_user");
        if(claims==null){
            return new Result(false,StatusCode.LOGINERROR,"权限不足");
        }

        //得到当前登录用户ID
        String userid= claims.getId();
        friendService.deleteFriend(userid,friendid);
        userClient.updateFanscountAndFollowcount(userid,friendid,-1);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
