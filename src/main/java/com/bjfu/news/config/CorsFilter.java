package com.bjfu.news.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author: wuxiaoru
 * @date: 2020/6/13 09:32
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "CorsFilter")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String origin = httpServletRequest.getHeader("Origin");
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Enumeration<String> headers = httpServletRequest.getHeaderNames();
        StringBuilder headersStr = new StringBuilder();
        headersStr.append("content-type,");
        while (headers.hasMoreElements()) {
            headersStr.append(headers.nextElement()).append(",");
        }
        String corsAllowHeaders = headersStr.toString().substring(0, headersStr.length() - 1);

        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", corsAllowHeaders);
        httpServletResponse.setHeader("Access-Control-Max-Age", "86400");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        if (HttpMethod.OPTIONS.toString().equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return;
        }

        chain.doFilter(request, response);
    }

}