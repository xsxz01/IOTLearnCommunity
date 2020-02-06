package cn.iotlearn.community.service;

import cn.iotlearn.community.dto.PaginationDTO;
import cn.iotlearn.community.dto.QuestionDTO;
import cn.iotlearn.community.mapper.QuestionMapper;
import cn.iotlearn.community.mapper.UserMapper;
import cn.iotlearn.community.model.Question;
import cn.iotlearn.community.model.QuestionExample;
import cn.iotlearn.community.model.User;
import cn.iotlearn.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExample(example);
        return getQuestionDTOS(questionList);
    }

    @NotNull
    private List<QuestionDTO> getQuestionDTOS(List<Question> questionList) {
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        int offset = (iPage-1)*iSize;   //计算出偏移量
        questionMapper.selectByExample(new QuestionExample());
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(
                example,
                new RowBounds(offset,iSize)
        );
        return getQuestionDTOS(questionList);
    }

    private List<QuestionDTO> listById(int id, String page, String size) {
        int iPage = Integer.parseInt(page);
        int iSize = Integer.parseInt(size);
        int offset = (iPage-1)*iSize;    //计算出偏移量
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(
                example,
                new RowBounds(offset,iSize)
        );
        return getQuestionDTOS(questionList);
    }

    public PaginationDTO listByPages(String page, String size){
        long questionCount = questionMapper.countByExample(new QuestionExample());
        int iPage = Integer.parseInt(page);
        int iSize = Integer.parseInt(size);
        int totalPageNum = (int)Math.ceil((float)questionCount / (float)iSize);
        if (iPage < 1) iPage = 1;
        if (iPage > totalPageNum)
            page = String.valueOf(iPage);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(listByPage(page,size));
        paginationDTO.setPageination(questionCount,page,size);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(int id, String page, String size) {
        int iPage = Integer.parseInt(page);
        int iSize = Integer.parseInt(size);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(id);
        long questionCount = questionMapper.countByExample(example);    // 取该用户的问题总数
        int totalPage = (int)Math.ceil((float)questionCount / (float)iSize);
        if (iPage < 1) page = "1";
        if (iPage > totalPage)  // 限制页码
            page = String.valueOf(totalPage);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setQuestions(listById(id, page, size));
        paginationDTO.setPageination(questionCount,page,size);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andIdEqualTo(id);
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExample(example);
        Question question = questions.get(0);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdEqualTo(question.getCreator());
        List<User> userList = userMapper.selectByExample(userExample);
        User user = userList.get(0);
        questionDTO.setUser(user);
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
            Question record = new Question();
            QuestionExample example = new QuestionExample();
            record.setGmtModified(System.currentTimeMillis());
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(record, example);
        }
    }
}
