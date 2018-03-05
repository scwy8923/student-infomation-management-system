package com.shuaizhao.project.serviceImpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuaizhao.project.exception.SqlExectorException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.mapper.UserMapper;
import com.shuaizhao.project.mapper.UserRoleMapper;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.UserExample;
import com.shuaizhao.project.model.UserExample.Criteria;
import com.shuaizhao.project.model.UserRole;
import com.shuaizhao.project.model.custom.DataGridResult;
import com.shuaizhao.project.model.custom.SearchBean;
import com.shuaizhao.project.service.StudentService;
import com.shuaizhao.project.utils.GloableConstance;
import com.shuaizhao.project.utils.MD5;
import com.shuaizhao.project.utils.MD5Untils;

@Service("studentService")
public class StudentServiceImpl implements StudentService{
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleMapper userRoleMapper;

	public void insertStudent(User user) throws SqlExectorException {
		user.setPassword(new MD5().getMD5ofStr(user.getUserId()));  //初始密码为学号
		user.setRoles(GloableConstance.ROLE_STUDENT+",");
		if (userMapper.insertSelective(user) != 1) {
			throw new SqlExectorException("服务器正忙。注册失败");
		}
		UserRole useRole=new UserRole();
		useRole.setSysUserId(user.getUserId());
		useRole.setSysRoleId(GloableConstance.ROLE_STUDENT_ID);
		userRoleMapper.insertSelective(useRole);
	}

	public List<User> findAllStudent() {
		
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andRolesLike("%"+GloableConstance.ROLE_STUDENT+"%");
		List<User> userList = userMapper.selectByExample(userExample);
		return userList;
	}

	public void updateStudent(User user) throws UserNotExistException {
		
		if (userMapper.updateByPrimaryKeySelective(user)==0) {
			throw new UserNotExistException("更新学生信息失败");
		}
	}

	public void deleteStudentrById(String id) throws UserNotExistException {
		if (userMapper.deleteByPrimaryKey(id) == 0) {
			throw new UserNotExistException("删除学生不存在");
		}
	}


	public List<User> findStudent(User user) {
		UserExample userExample=new UserExample();
		Criteria createCriteria = userExample.createCriteria();
		createCriteria.andRolesLike("%"+GloableConstance.ROLE_STUDENT+"%");
		if(user.getUserId()!=null){
			createCriteria.andUserIdLike(user.getUserId());
		}
		if(user.getName()!=null){
			createCriteria.andNameLike(user.getName());
		}
		List<User> list = userMapper.selectByExample(userExample);
		
		return list;
	}

	/**
	 *<p>Description: </p>
	 * @author forever
	 * @date 2017年6月3日
	*/
	public User findStuentByUserId(String userId) {
		// TODO Auto-generated method stub
		UserExample example=new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRolesLike("%"+GloableConstance.ROLE_STUDENT+"%");
		criteria.andUserIdEqualTo(userId);
		List<User> selectByExample = userMapper.selectByExample(example);
		return selectByExample.isEmpty()?null:selectByExample.get(0);
	}

	@Override
	public DataGridResult getGrid(int page,int rows,SearchBean searchBean) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andRolesLike("%"+GloableConstance.ROLE_STUDENT+"%");
		List<User> list;
		if(searchBean.getSearchName()==null||(searchBean.getSearchName().isEmpty()&&searchBean.getSearchUserId().isEmpty())){
			list=findAllStudent();
		}
		else{
			if(!searchBean.getSearchName().isEmpty()){
				criteria.andNameLike("%"+searchBean.getSearchName()+"%");
			}
			if(!searchBean.getSearchUserId().isEmpty()){
				criteria.andUserIdLike("%"+searchBean.getSearchUserId()+"%");
			}
			list = userMapper.selectByExample(userExample);
		}
		DataGridResult result=new DataGridResult();
		result.setRows(list);
		PageInfo<User> pageInfo=new PageInfo<User>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}


}
