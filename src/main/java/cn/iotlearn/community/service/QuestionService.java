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

    private List<QuestionDTO> listById(int id, String page, String size) {
        int iPage = Integer.parseInt(page);
        int iSize = Integer.parseInt(size);
        int offset = (iPage-1)*iSize;    //计算出偏移量
        List<Question> questionList = questionMapper.listByUserId(id,offset,iSize);
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

    public PaginationDTO listByUserId(int id, String page, String size) {
        if (Integer.parseInt(page) < 1) page = "1";
        if (Integer.parseInt(page) > questionMapper.count()/Integer.parseInt(size))
            page = String.valueOf(questionMapper.count()/Integer.parseInt(size));
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(id);
        paginationDTO.setQuestions(listById(id, page, size));
        paginationDTO.setPageination(totalCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User userMapperById = userMapper.findById(question.getCreator());
        questionDTO.setUser(userMapperById);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
//            创建
            question.setLikeCount(0);
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
//            更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
