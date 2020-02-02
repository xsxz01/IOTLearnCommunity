package cn.iotlearn.community.controller;
import cn.iotlearn.community.dto.PaginationDTO;
import cn.iotlearn.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    public String index(
            Model model,
            @RequestParam(name = "page",defaultValue = "1",required = false) String page,
            @RequestParam(name = "size",defaultValue = "15",required = false) String size
    ) {
        PaginationDTO questionDTOList = questionService.listByPages(page,size);
        model.addAttribute("questionDTOList",questionDTOList);
        return "index";
    }
}
