package cn.iotlearn.community.service;

import cn.iotlearn.community.dto.PaginationDTO;
import cn.iotlearn.community.dto.QuestionDTO;
import cn.iotlearn.community.mapper.QuestionMapper;
import cn.iotlearn.community.mapper.UserMapper;
import cn.iotlearn.community.model.Question;
import cn.iotlearn.community.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<QuestionDTO> listAll() {
        List<Question> questionList = questionMapper.listAll();
        return getQuestionDTOS(questionList);
    }

    @NotNull
    private List<QuestionDTO> getQuestionDTOS(List<Question> questionList) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    private List<QuestionDTO> listByPage(String page, String size) {
        int iPage = Integer.parseInt(page);
        int iSize = Integer.parseInt(size);
        int offset = (iPage-1)*iSize;    //计算出偏移量
        List<Question> questionList = questionMapper.listByPage(offset,iSize);
        return getQuestionDTOS(questionList);
    }
    public PaginationDTO listByPages(String page, String size){
        if (Integer.parseInt(page) < 1) page = "1";
        if (Integer.parseInt(page) > questionMapper.count()/Integer.parseInt(size))
            page = String.valueOf(questionMapper.count()/Integer.parseInt(size));
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setQuestions(listByPage(page,size));
        paginationDTO.setPageination(totalCount,page,size);
        return paginationDTO;
    }
}
