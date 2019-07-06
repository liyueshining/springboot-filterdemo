package com.example.demo.filter;

import com.example.demo.entity.HMessage;
import com.example.demo.http.RedistribService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Call;
import retrofit2.Response;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "requestRedirectFilter")
public class RequestRedirectFilter implements Filter{
    private static final Logger logger = LoggerFactory.getLogger(RequestRedirectFilter.class);

    @Autowired
    RedistribService service;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String distri = servletRequest.getParameter("distribute");
        logger.info("distribute is : " + distri);

        if (distri != null){
            logger.info("already distributed");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.info("need to redirect to other node");
            Call<HMessage> result = service.getHelloWorld("http://localhost:8089/admin/hello?distribute=true");
            Response<HMessage> res = result.execute();

            int statusCode = res.code();
            ((HttpServletResponse)servletResponse).setStatus(statusCode);
            if( statusCode != 200){
                servletResponse.getWriter().print(res.errorBody().string());
                res.errorBody().close();
            } else {
                HMessage message = res.body();
                servletResponse.getWriter().print(new ObjectMapper().writeValueAsString(message));
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(((HttpServletRequest) servletRequest).getMethod())
                .append(",")
                .append(((HttpServletRequest) servletRequest).getRequestURI())
                .append(",")
                .append(((HttpServletResponse) servletResponse).getStatus())
                .append(",")
                .append(servletRequest.getProtocol())
                .append(",")
                .append(servletRequest.getRemoteHost())
                .append(",")
                .append(((HttpServletRequest) servletRequest).getHeader("user-agent"));
        logger.info(builder.toString());
    }
}
