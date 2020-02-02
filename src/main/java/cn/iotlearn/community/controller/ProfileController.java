package cn.iotlearn.community.controller;
import cn.iotlearn.community.dto.PaginationDTO;
import cn.iotlearn.community.model.User;
import cn.iotlearn.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(
            HttpServletRequest request,
            Model model,
            @PathVariable(name = "action",required = false) String action,
            @RequestParam(name = "page",defaultValue = "1",required = false) String page,
            @RequestParam(name = "size",defaultValue = "15",required = false) String size){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        // 避免出现空指针
        if ("questions".equals(action)){
            PaginationDTO paginationDTO = questionService.listByUserId(Integer.parseInt(user.getAccountId()), page, size);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            model.addAttribute("paginationDTO",paginationDTO);
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
