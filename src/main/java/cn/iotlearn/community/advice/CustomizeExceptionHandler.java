package cn.iotlearn.community.advice;

import cn.iotlearn.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model){
        HttpStatus status = getStatus(request);
        if (ex instanceof CustomizeException){
            model.addAttribute("msg",ex.getMessage());
        }else {
            model.addAttribute("msg","滴油，程序又错了。");
        }
        return new ModelAndView("error",status);
    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(status);
    }

}
