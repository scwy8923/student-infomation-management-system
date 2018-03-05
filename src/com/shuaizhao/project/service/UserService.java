package com.shuaizhao.project.service;

import java.util.List;

import com.shuaizhao.project.exception.PasswordPatternException;
import com.shuaizhao.project.exception.PhonePatternException;
import com.shuaizhao.project.exception.RoleNotExistException;
import com.shuaizhao.project.exception.UserIdHasBeenUsedException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.exception.UserPasswordNotMatchException;
import com.shuaizhao.project.exception.UserRoleExistException;
import com.shuaizhao.project.model.Permission;
import com.shuaizhao.project.model.Role;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.custom.ActiveUser;

public interface UserService {

	public void registe(User user) throws UserIdHasBeenUsedException, PasswordPatternException, PhonePatternException;
	
	public ActiveUser login(String phone,String password) throws UserPasswordNotMatchException;
	
	public boolean checkUserExist(String userId);
	
	public User findUserById(String UserId);
	
	public List<Permission> findMenuById(String userId);
	
	public List<Permission> findPermissionById(String userId);
	
	public List<Role> findRoles(String userId) throws UserNotExistException;
	
	public void addRole(String userId,String roleId) throws UserRoleExistException, UserNotExistException, RoleNotExistException;
	
	public void updatePassword(String userId,String oldPassword,String newPassword) throws UserPasswordNotMatchException;
}
