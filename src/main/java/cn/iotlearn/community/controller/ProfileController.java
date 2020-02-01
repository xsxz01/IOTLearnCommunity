package cn.iotlearn.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action",required = false) String action,
                          Model model
                          ){
        // 避免出现空指针
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","回复");
        }else if ("settings".equals(action)){
            model.addAttribute("section","settings");
            model.addAttribute("sectionName","个人资料");
        }else {
            model.addAttribute("section","error");
            model.addAttribute("sectionName","模块未找到");
        }
        return "profile";
    }
}
