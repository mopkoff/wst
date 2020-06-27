package com.itmo.wst.config;

import com.itmo.wst.fault.exception.ThrottlingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ThrottlingInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = LoggerFactory.getLogger(ThrottlingInterceptor.class);

    private int processes_count = 0;
    private static final int MAX_PROCESSING_COUNT = 1;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        if (incCounter() > MAX_PROCESSING_COUNT) {
            ThrottlingException throttlingException = new ThrottlingException("Maximum processes reached. This request forbidden. Try again later");
            response.getWriter().write(throttlingException.getMessage());
            throw throttlingException;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        decCounter();
    }

    private synchronized int incCounter(){
        log.info("inc");
        return ++processes_count;
    }

    private synchronized int decCounter(){
        log.info("dec");
        return --processes_count;
    }
}
