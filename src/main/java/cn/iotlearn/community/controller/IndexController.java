package cn.iotlearn.community.controller;

import cn.iotlearn.community.dto.GithubUserDTO;
import cn.iotlearn.community.dto.QuestionDTO;
import cn.iotlearn.community.mapper.QuestionMapper;
import cn.iotlearn.community.mapper.UserMapper;
import cn.iotlearn.community.model.Question;
import cn.iotlearn.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    //                System.out.println(cookie.getValue());
                    String token = cookie.getValue();
                    GithubUserDTO user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionDTOList = questionService.listAll();
        model.addAttribute("questions",questionDTOList);
        return "index";
    }
}
