/**
 * 
 */
package com.shuaizhao.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.shuaizhao.project.model.Permission;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.service.UserService;

/**
 * <p>
 * Title:CustomRealm
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author forever
 * @date 2017年6月3日
 */
public class CustomRealm extends AuthorizingRealm {

	@Autowired
	UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.shiro.realm.CachingRealm#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CustomRealm";
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		ActiveUser user = (ActiveUser) principals.getPrimaryPrincipal();
		List<Permission> permissionList = user.getPermissions();
		
		List<String> permissions = new ArrayList<>();
		

		if (permissionList != null) {
			for (Permission permission : permissionList) {
				permissions.add(permission.getPercode());
			}
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		String userId = (String) token.getPrincipal();
		ActiveUser activeUser = new ActiveUser();
		User user = userService.findUserById(userId);
		if (user == null) {
			return null;
		}
		List<Permission> menuList = userService.findMenuById(user.getUserId());
		List<Permission> permissionList = userService.findPermissionById(userId);
		activeUser.setPermissions(permissionList);
		activeUser.setMenus(menuList);
		activeUser.setUserid(user.getUserId());
		activeUser.setUsername(user.getName());
		activeUser.setUsercode(userId);
		String password = user.getPassword();

		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password,
				getName());
		
		return simpleAuthenticationInfo;
	}

	public void clearCache() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
