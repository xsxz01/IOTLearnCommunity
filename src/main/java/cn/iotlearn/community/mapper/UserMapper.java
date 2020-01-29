package cn.iotlearn.community.mapper;

import cn.iotlearn.community.dto.GithubUserDTO;
import cn.iotlearn.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into User (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name}," +
            "#{token},#{gmtCreate},#{gmtModified})")
    void insertUser(User user);
    @Select("select * from User where token = #{token}")
    GithubUserDTO findByToken(@Param("token")String token);
}
