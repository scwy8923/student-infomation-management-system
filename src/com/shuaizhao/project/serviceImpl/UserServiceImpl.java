package com.shuaizhao.project.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shuaizhao.project.exception.PasswordPatternException;
import com.shuaizhao.project.exception.PhonePatternException;
import com.shuaizhao.project.exception.RoleNotExistException;
import com.shuaizhao.project.exception.UserIdHasBeenUsedException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.exception.UserPasswordNotMatchException;
import com.shuaizhao.project.exception.UserRoleExistException;
import com.shuaizhao.project.mapper.CustomPermissionMapper;
import com.shuaizhao.project.mapper.RoleMapper;
import com.shuaizhao.project.mapper.UserMapper;
import com.shuaizhao.project.mapper.UserRoleMapper;
import com.shuaizhao.project.model.Permission;
import com.shuaizhao.project.model.Role;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.UserExample;
import com.shuaizhao.project.model.UserExample.Criteria;
import com.shuaizhao.project.model.UserRole;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.service.UserService;
import com.shuaizhao.project.utils.CheckUtil;
import com.shuaizhao.project.utils.MD5;
import com.shuaizhao.project.utils.MD5Untils;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleMapper userrRoleMapper;
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	CustomPermissionMapper customPermissionMapper;
	public void registe(User user) throws UserIdHasBeenUsedException, PasswordPatternException, PhonePatternException{
		if(checkUserExist(user.getPhone())){
			throw new UserIdHasBeenUsedException("手机号已被注册");
		}
		
		if(!CheckUtil.checkPassword(user.getPassword())){
			throw new PasswordPatternException("密码格式不正确");
		}
		
		if(!CheckUtil.checkPhone(user.getPhone())){
			throw new PhonePatternException("手机号码格式不正确");
		}
		
		user.setPassword(MD5Untils.md5(user.getPassword()));
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
		if(results==null||results.size()==0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public User findUserById(String userId){
		UserExample example=new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<User> selectByExample = userMapper.selectByExample(example);
		return selectByExample==null?null:selectByExample.get(0);
	}
	
	public List<Permission> findMenuById(String userId){
		return customPermissionMapper.selectMenuByUserId(userId);
	}
	
	public List<Permission> findPermissionById(String userId){
		return customPermissionMapper.selectPremissionByUserId(userId);
	}

	@Override
	public List<Role> findRoles(String userId) throws UserNotExistException {
		if(!checkUserExist(userId)){
			throw new UserNotExistException("学号/工号信息不存在");
		}
		return customPermissionMapper.getRolesByUserId(userId);
	}

	@Override
	public void addRole(String userId, String roleId) throws UserRoleExistException, UserNotExistException, RoleNotExistException {
		// TODO Auto-generated method stub
		if(checkUserRoleExist(userId, roleId)){
			throw new UserRoleExistException("用户权限已存在");
		}
		User user = findUserById(userId);
		if(user==null){
			throw new UserNotExistException("用户信息不存在");
		}
		Role role = getRoleById(roleId);
		if(role==null){
			throw new RoleNotExistException("权限信息存在");
		}
		user.setRoles(user.getRoles()+","+role.getName());
		userMapper.updateByPrimaryKeySelective(user);
		UserRole userRole=new UserRole();
		userRole.setSysRoleId(roleId);
		userRole.setSysUserId(userId);
		userrRoleMapper.insert(userRole);
	}
	
	public boolean checkUserRoleExist(String userId,String roleId){
		UserRole userRole=new UserRole();
		userRole.setSysRoleId(roleId);
		userRole.setSysUserId(userId);
		int result = customPermissionMapper.checkUserRoleExist(userRole);
		if(result==1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Role getRoleById(String roleId){
		Role role = roleMapper.selectByPrimaryKey(roleId);
		return role;
	}

	@Override
	public void updatePassword(String userId,String oldPassword, String newPassword) throws UserPasswordNotMatchException {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andPasswordEqualTo(new MD5().getMD5ofStr(oldPassword));
		List<User> selectByExample = userMapper.selectByExample(userExample);
		if(selectByExample==null||selectByExample.size()==0){
			throw new UserPasswordNotMatchException("原密码错误");
		}
		User user=new User();
		user.setUserId(userId);
		user.setPassword(new MD5().getMD5ofStr(newPassword));
		userMapper.updateByPrimaryKeySelective(user);
		
	}
	
	
}
