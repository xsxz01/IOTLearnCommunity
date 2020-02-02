package cn.iotlearn.community.mapper;

import cn.iotlearn.community.dto.GithubUserDTO;
import cn.iotlearn.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into User (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountId}," +
            "#{name}," +
            "#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insertUser(User user);
    @Select("select * from User where token = #{token}")
    User findByToken(@Param("token")String token);
    @Select("select * from User where account_id =#{id} order by GMT_CREATE desc limit 0,1")
    User findById(@Param("id")Integer creator);
}
