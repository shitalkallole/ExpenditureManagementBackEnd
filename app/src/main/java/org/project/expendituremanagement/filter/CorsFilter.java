package org.project.expendituremanagement.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //"preflight request" are made by browser by OPTIONS request method for validating "resource request"(cross origin request)
        //We need to set this three below header in each request ,for preflight request and for resource request
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT");//we are not allowing any user "resource request" which has OPTION as request method
        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");//check which header u want to allow(basic-userId,sessionId)

        if(httpServletRequest.getMethod().equalsIgnoreCase("OPTIONS"))//for preflight request OPTIONS method used
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);//for preflight request we are returning directly from here, because if it goto next filter then there we check userId and sessionId and which is not present in preflight request
        else
            filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
