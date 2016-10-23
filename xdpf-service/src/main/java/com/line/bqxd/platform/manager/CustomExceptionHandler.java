package com.line.bqxd.platform.manager;

import com.line.bqxd.platform.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/18.
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);
        model.put("errorCode","sysError");
        model.put("errorDesc","系统异常");
        logger.error("custom exception handle",ex);
        // 根据不同错误转向不同页面
        if(ex instanceof BusinessException) {
            return new ModelAndView("error-business", model);
        }
        else {
            return new ModelAndView("error", model);
        }
    }
}
