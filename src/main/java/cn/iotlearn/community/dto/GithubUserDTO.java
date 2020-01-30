package cn.iotlearn.community.dto;

import lombok.Data;

@Data
public class GithubUserDTO {
    private String name;
    private int id;
    private String bio;
    private String avatar_url;
}
