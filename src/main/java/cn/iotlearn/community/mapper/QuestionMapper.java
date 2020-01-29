package cn.iotlearn.community.mapper;

import cn.iotlearn.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,like_count,tag,creator,comment_count,view_count,gmt_modified," +
            "gmt_create) values (#{title},#{description},#{likeCount},#{tag},#{creator},#{commentCount}," +
            "#{viewCount}," +
            "#{gmtModified},#{gmtCreate})")
    void insert(Question question);
}
