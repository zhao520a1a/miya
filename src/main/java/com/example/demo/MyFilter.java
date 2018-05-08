package com.example.demo;

import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebFilter;

/**
 * @author 赵金鑫
 * <p>
 * 过滤器
 */
@Slf4j
@Component
@WebFilter(filterName = "MyFilter", urlPatterns = "/", asyncSupported = true)
public class MyFilter extends ZuulFilter {

    /*    定义过滤器类型
    zuul中定义了四种不同生命周期的过滤器类型
        pre：路由之前
        routing：路由之时
        post： 路由之后
         error：发送错误调用
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /*
    定义过滤顺序
     */
    @Override
    public int filterOrder() {
        return 0; // 优先级为0，数字越大，优先级越低
    }

    /*
    这里可以写逻辑判断，是否要过滤，本文true,永远过滤
     */
    @Override
    public boolean shouldFilter() {
//        return true;
        return false;
    }

    /*
    过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     */
    @Override
    public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        HttpServletRequest request = ctx.getRequest();
//        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
//        Object accessToken = request.getParameter("token");  //获取请求中的token参数，判空
//        if(accessToken == null) {
//            log.warn("token is empty");
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            try {
//                ctx.getResponse().getWriter().write("token is empty");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//        log.info("ok");
        return null;
    }
}
