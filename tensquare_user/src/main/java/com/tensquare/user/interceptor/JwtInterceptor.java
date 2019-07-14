package com.tensquare.user.interceptor;

import com.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String header=request.getHeader("Authorization1");
        if(header!=null && !"".equals(header)){
           //如果有包含Authorization头信息，就对其进行解析
            if(header.startsWith("Bearer")){
                //得到token
                String token=header.substring(7);
                try {
                    Claims claims=jwtUtil.parseJWT(token);
                    String roles= (String) claims.get("roles");
                    if(roles!=null || roles.equals("admin")){

                        request.setAttribute("claims_admin",token);

                    }

                    if(roles!=null || roles.equals("user")){

                        request.setAttribute("claims_user",token);

                    }
                }catch (Exception e){
                    throw new RuntimeException("令牌不正确");
                }
            }

            return true;
        }
        return true;
    }
}
