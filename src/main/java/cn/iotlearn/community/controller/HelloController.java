package cn.iotlearn.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @RequestMapping("/getHello")
    public String getHello(@RequestParam(name = "name", required = false, defaultValue = "hello")
                                   String name,
                           Model model) {
        model.addAttribute("name", name);
        return "greeting"; // 控制要打开的页面
    }
}
