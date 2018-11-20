package com.itzixi.mapper;

import com.itzixi.pojo.StudentItem;
import com.itzixi.pojo.StudentItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentItemMapper {
    int countByExample(StudentItemExample example);

    int deleteByExample(StudentItemExample example);

    int deleteByPrimaryKey(String id);

    int insert(StudentItem record);

    int insertSelective(StudentItem record);

    List<StudentItem> selectByExample(StudentItemExample example);

    StudentItem selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StudentItem record, @Param("example") StudentItemExample example);

    int updateByExample(@Param("record") StudentItem record, @Param("example") StudentItemExample example);

    int updateByPrimaryKeySelective(StudentItem record);

    int updateByPrimaryKey(StudentItem record);
}