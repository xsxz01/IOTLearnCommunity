package cn.iotlearn.community.controller;

import cn.iotlearn.community.dto.QuestionDTO;
import cn.iotlearn.community.mapper.QuestionMapper;
import cn.iotlearn.community.model.User;
import cn.iotlearn.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(
            HttpServletRequest request,
            @PathVariable("id") Integer id,
            Model model
    ){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("questionDTO",questionDTO);
        // 判断权限
        User user = (User) request.getSession().getAttribute("user");
        boolean isCreator = false;
        if (user != null){
            isCreator = questionDTO.getUser().getAccountId().equals(user.getAccountId());
        }
        model.addAttribute("isCreator",isCreator);
        return "question";
    }
}
