package com.shuaizhao.project.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shuaizhao.project.exception.PasswordPatternException;
import com.shuaizhao.project.exception.UserIdHasBeenUsedException;
import com.shuaizhao.project.exception.PhonePatternException;
import com.shuaizhao.project.exception.SqlExectorException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.exception.UserPasswordNotMatchException;
import com.shuaizhao.project.mapper.CustomPermissionMapper;
import com.shuaizhao.project.mapper.UserMapper;
import com.shuaizhao.project.mapper.UserRoleMapper;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.UserExample;
import com.shuaizhao.project.model.UserExample.Criteria;
import com.shuaizhao.project.model.UserRole;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.model.custom.DataGridResult;
import com.shuaizhao.project.model.custom.SearchBean;
import com.shuaizhao.project.service.TeacherService;
import com.shuaizhao.project.utils.CheckUtil;
import com.shuaizhao.project.utils.GloableConstance;
import com.shuaizhao.project.utils.MD5;
import com.shuaizhao.project.utils.MD5Untils;

@Service("teacherService")
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	CustomPermissionMapper customPermissionMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	public void addTeacher(User user) throws UserIdHasBeenUsedException, PasswordPatternException, PhonePatternException{
		if(checkUserExist(user.getUserId())){
			throw new UserIdHasBeenUsedException("教工号已被注册");
		}
		
		
		if(!CheckUtil.checkPhone(user.getPhone())){
			throw new PhonePatternException("手机号码格式不正确");
		}
		UserRole userRole=new UserRole();
		userRole.setSysRoleId(GloableConstance.ROLE_TEACHER_ID);
		userRole.setSysUserId(user.getUserId());
		userRoleMapper.insertSelective(userRole);
		user.setPassword(new MD5().getMD5ofStr(user.getUserId()));
		user.setRoles(GloableConstance.ROLE_TEACHER+",");
		userMapper.insertSelective(user);
		
	}
	
	public ActiveUser login(String phone,String password) throws UserPasswordNotMatchException{
		
		UserExample example=new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andPhoneEqualTo(phone);
		createCriteria.andPasswordEqualTo(MD5Untils.md5(password));
		List<User> results = userMapper.selectByExample(example);
		if(results==null||results.size()!=1){
			throw new UserPasswordNotMatchException("用户名或密码错误");
		}
		User user=results.get(0);
		ActiveUser activeUser=new ActiveUser();
		activeUser.setUsername(user.getName());
		activeUser.setUserid(user.getUserId());
		activeUser.setMenus(customPermissionMapper.selectMenuByUserId(user.getUserId()));
		activeUser.setPermissions(customPermissionMapper.selectPremissionByUserId(user.getUserId()));
		return activeUser;
		
	}
	
	public boolean checkUserExist(String userId){
		UserExample example=new UserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUserIdEqualTo(userId);
		List<User> results = userMapper.selectByExample(example);
		if(results.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public User findTeacherById(String UserId){
		UserExample example=new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(UserId);
		criteria.andRolesLike("%"+GloableConstance.ROLE_TEACHER+"%");
		List<User> selectByExample = userMapper.selectByExample(example);
		return selectByExample.isEmpty()?null:selectByExample.get(0);
	}

	@Override
	public void updateTeacher(User user) throws SqlExectorException {
		// TODO Auto-generated method stub
		if(userMapper.updateByPrimaryKeySelective(user)!=1){
			throw new SqlExectorException("服务器错误！");
		}
	}

	@Override
	public void deleteTeacher(User user) throws UserNotExistException {
		if(userMapper.deleteByPrimaryKey(user.getUserId())!=1){
			throw new UserNotExistException("教师信息不存在");
		}
	}

	@Override
	public List<User> findAllTeacher() {
		// TODO Auto-generated method stub
		UserExample example=new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andRolesLike("%"+GloableConstance.ROLE_TEACHER+"%");
		List<User> userList = userMapper.selectByExample(example);
		return userList;
	}

	@Override
	public DataGridResult getGrid(int page, int rows, SearchBean searchBean) {
		PageHelper.startPage(page, rows);
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andRolesLike("%"+GloableConstance.ROLE_TEACHER+"%");
		List<User> list;
		if(searchBean.getSearchName()==null||(searchBean.getSearchName().isEmpty()&&searchBean.getSearchUserId().isEmpty())){
			list=findAllTeacher();
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
