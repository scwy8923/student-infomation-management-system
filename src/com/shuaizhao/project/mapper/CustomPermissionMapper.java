package com.shuaizhao.project.mapper;

import java.util.List;

import com.shuaizhao.project.model.Permission;
import com.shuaizhao.project.model.Role;
import com.shuaizhao.project.model.UserRole;

public interface CustomPermissionMapper {
	
	List<Permission> selectMenuByUserId(String userId);
	
	List<Permission> selectPremissionByUserId(String userId);
	
	List<Role> getRolesByUserId(String userId);
	
	int checkUserRoleExist(UserRole userRole);

}
