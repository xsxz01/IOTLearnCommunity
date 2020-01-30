package cn.iotlearn.community.dto;

import cn.iotlearn.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer likeCount;
    private String tag;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Long gmtModified;
    private Long gmtCreate;
    private User user;
}
