package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

public class ParseJwtTest {
    public static void main(String[] args) {
        try {
            Claims claims= Jwts.parser().setSigningKey("iscast")
                    .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTU2MjY4MTUwNiwiZXhwIjoxNTYyNjgxNTY2LCJyb2xlIjoiYWRtaW4ifQ.fyJp-BPWgOKuWCRGsZoLySgag3X8SSHXGA0hmLv53eU")
                    .getBody();
            System.out.println("用户Id:"+claims.getId());
            System.out.println("用户名:"+claims.getSubject());
            System.out.println("登陆时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()) );
            System.out.println("用户角色:"+ claims.get("role"));
        }catch (Exception e){
            System.out.println("令牌已过期!");
        }


    }
}
