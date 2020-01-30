package cn.iotlearn.community.model;

import lombok.Data;

@Data
public class Question {
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

}
