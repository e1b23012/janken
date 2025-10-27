package oit.is.z2819.kaizi.janken.model;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * from users")
  ArrayList<User> selectAllUsers();

  @Select("SELECT * from users WHERE id = #{id}")
  User selectUserById(int id);

  @Select("SELECT * from users WHERE name = #{name}")
  User selectUserByName(String name);

}
