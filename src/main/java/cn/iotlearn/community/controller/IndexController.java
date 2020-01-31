package cn.iotlearn.community.controller;

import cn.iotlearn.community.dto.GithubUserDTO;
import cn.iotlearn.community.dto.PaginationDTO;
import cn.iotlearn.community.dto.QuestionDTO;
import cn.iotlearn.community.mapper.UserMapper;
import cn.iotlearn.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    public String index(
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "page",defaultValue = "1",required = false) String page,
            @RequestParam(name = "size",defaultValue = "15",required = false) String size
    ) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    GithubUserDTO user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO questionDTOList = questionService.listByPages(page,size);
        model.addAttribute("questionDTOList",questionDTOList);
        return "index";
    }
}
