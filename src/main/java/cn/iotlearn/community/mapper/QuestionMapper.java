package cn.iotlearn.community.mapper;

import cn.iotlearn.community.dto.QuestionDTO;
import cn.iotlearn.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,like_count,tag,creator,comment_count,view_count,gmt_modified," +
            "gmt_create) values (#{title},#{description},#{likeCount},#{tag},#{creator},#{commentCount}," +
            "#{viewCount}," +
            "#{gmtModified},#{gmtCreate})")
    void insert(Question question);

    @Select("select * from question order by gmt_modified desc")
    List<Question> listAll();

    @Select("select * from question order by gmt_modified desc limit #{offset},#{size}")
    List<Question> listByPage(@Param("offset")Integer offset, @Param("size")Integer size);

    @Select("select count(id) from question")
    Integer count();

    @Select("select count(id) from question where creator = #{id}")
    Integer countByUserId(@Param("id") Integer id);

    @Select("select * from question where creator = #{id} order by gmt_modified desc limit #{offset},#{size}")
    List<Question> listByUserId(@Param("id")int id, @Param("offset")int offset, @Param("size")int iSize);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id")Integer id);
}
