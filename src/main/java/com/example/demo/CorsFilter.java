//package com.example.demo;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///* cors:(Cross-Origin Resource Sharing, 跨源资源共享)
// *
//  *
//  *  Spring Cloud 项目出现 Options Forbidden 403 问题解决 :
//  *  CORS 请求分为2类： 简单请求 和 非简单请求。两者主要的区分点在于：
//* 1: 请求方法为 HEAD, GET, POST;
//* 2: HTTP 头信息为以下几个： Accept， Accept-Language，Content-Language， Last-Event-ID，Content-Type （值为 application/x-www-form-urlencoded、multipart/form-data、text/plain）。
//  只要满足以上两点，则为简单请求；否则为非简单请求。
//简单请求的处理方式是浏览器直接发送 CORS 请求。非简单请求的处理方式是浏览器发送预检请求，表示询问服务器当前的域名是否可以访问正常服务器，如果可以访问，则发送正常的请求到服务器；否则报错。
//  现在确定遇到的问题就是在 CORS 请求预检的时候发现域名不在服务器端的白名单里面，所以需要修改服务端的请求返回报文。
//  *
//  *
//  * */
//
//@WebFilter(filterName = "corsFilter", urlPatterns ="/", asyncSupported = true)
//@Component
//public class CorsFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletResponse response = (HttpServletResponse) res;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//        response.setHeader("Access-Control-Expose-Headers", "Location");
//        chain.doFilter(req, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {}
//
//    @Override
//    public void destroy() {}
//}
