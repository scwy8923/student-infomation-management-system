package com.shuaizhao.project.mapper;

import com.shuaizhao.project.model.Institute;
import com.shuaizhao.project.model.InstituteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstituteMapper {
    int countByExample(InstituteExample example);

    int deleteByExample(InstituteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Institute record);

    int insertSelective(Institute record);

    List<Institute> selectByExample(InstituteExample example);

    Institute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Institute record, @Param("example") InstituteExample example);

    int updateByExample(@Param("record") Institute record, @Param("example") InstituteExample example);

    int updateByPrimaryKeySelective(Institute record);

    int updateByPrimaryKey(Institute record);
}