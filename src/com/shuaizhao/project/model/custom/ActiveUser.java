/**
 * 
 */
package com.shuaizhao.project.model.custom;

import java.util.List;

import com.shuaizhao.project.model.Permission;

/**
 *<p>Title:ActiveUser </p>
 *<p>Description: </p>
 * @author forever
 * @date 2017年6月1日
*/
public class ActiveUser {
	private String userid;//用户id（主键）
	private String usercode;// 用户账号
	private String username;// 用户名称
	private String userType;

	private List<Permission> menus;// 菜单
	private List<Permission> permissions;// 权限

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Permission> getMenus() {
		return menus;
	}

	public void setMenus(List<Permission> menus) {
		this.menus = menus;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
