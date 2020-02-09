package cn.iotlearn.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller("${server.error.path:${error.path:/error}}")
public class CustomizeController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(
            HttpServletRequest request,
            Model model
    ){
        HttpStatus status = getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("msg","您的请求有误，要不换个姿势？");
        }else if (status.is5xxServerError()){
            model.addAttribute("msg","服务器开小差了，请稍后再试");
        }

        return new ModelAndView("error",status);
    }
    private HttpStatus getStatus(HttpServletRequest request){
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(status);
        }catch (Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
