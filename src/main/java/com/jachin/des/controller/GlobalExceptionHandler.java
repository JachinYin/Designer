package com.jachin.des.controller;

import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jachin
 * @since 2019/4/6 08:52
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Response defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
//        logger.error("", e);
        Response response = new Response();
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            response.setCode(404);
            response.setMsg("丢失了哦~");
        }
//        if(e instanceof )
        else {
            response.setCode(500);
            response.setMsg("操作失败。");
        }
        response.setSuccess(false);
        return response;
    }

}
