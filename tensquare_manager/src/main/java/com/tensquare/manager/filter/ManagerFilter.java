package com.tensquare.manager.filter;

import com.common.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;
    /*
    * 在请求前(pre)或者后(post)执行
    * */
    @Override
    public String filterType() {
        return "pre";
    }
    /*
     * 多个过滤器的执行顺序返回 0123456
     * */
    @Override
    public int filterOrder() {
        return 0;
    }
    /*
     * 当前过滤器是否开启，返回true表示开启，false表示关闭当前过滤器
     *
     * */
    @Override
    public boolean shouldFilter() {
        return true;
    }
    /*
     * 过滤器内执行的操作return 任何object的值都表示继续执行
     *setsendzullResppones(false)表示不再继续执行
     * */
    @Override
    public Object run() throws ZuulException {
        System.out.println("途径后台过滤器");
        //得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //得到request域
        HttpServletRequest request = currentContext.getRequest();

        if(request.getMethod().equals("OPTIONS")){
            return null;
        }
        if (request.getRequestURI().indexOf("/admin/login")>0){
            return null;
        }

        //得到头信息
        String header = request.getHeader("Authorization1");
        //判断是否有头信息
        if(header != null && !"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token=header.substring(7);
                try{
                    Claims claims=jwtUtil.parseJWT(token);
                    String roles=(String)claims.get("roles");
                    if(roles!=null || roles.equals("admin")){
                        //把头信息继续向下传
                        currentContext.addZuulRequestHeader("Authorization1", header);
                        return null;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);//终止运行
                }
            }
        }

        currentContext.setSendZuulResponse(false);//终止运行
        currentContext.setResponseStatusCode(403);//错误类型
        currentContext.setResponseBody("权限不足");//错误提示信息
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        //currentContext.getResponse().setContentType("application/json;charset=utf-8");
        return null;
    }
}
