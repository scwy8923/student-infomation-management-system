/**
 * 
 */
package com.shuaizhao.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 *<p>Title:CustomAuthenticationToken </p>
 *<p>Description: </p>
 * @author forever
 * @date 2017年6月3日
*/
public class CustomAuthenticationToken extends UsernamePasswordToken{

	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
}
