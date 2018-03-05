/**
 * 
 */
package com.shuaizhao.shiro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 *<p>Title:CustomFormAuthenticationFilter </p>
 *<p>Description: </p>
 * @author forever
 * @date 2017年6月3日
*/
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter{



	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		CustomAuthenticationToken token=new CustomAuthenticationToken();
		token.setUserId(request.getParameter("userId"));
		return super.createToken(request, response);
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onAccessDenied(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		String realCode = (String) httpRequest.getSession().getAttribute("validateCode");
		String randomCode = httpRequest.getParameter("randomcode");
		if(randomCode!=null&&realCode!=null&&!realCode.equals(randomCode)){
			httpRequest.setAttribute("shiroLoginFailure", "randomCodeError");
			return true;
		}
		return super.onAccessDenied(request, response);
	}
	


	
}
