/**
 * 
 */
package com.shuaizhao.intercepter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.shuaizhao.project.model.Permission;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.utils.ResourcesUtil;

/**
 * <p>
 * Title:PermissionIntercepter
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author forever
 * @date 2017年6月1日
 */
public class PermissionIntercepter extends MethodFilterInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept(
	 * com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub

		// 得到请求的url
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURI();

		// 判断是否是公开 地址
		// 实际开发中需要公开 地址配置在配置文件中
		// 从配置中取逆名访问url

		List<String> open_urls = ResourcesUtil.gekeyList("anonymousURL");
		// 遍历公开 地址，如果是公开 地址则放行
		for (String open_url : open_urls) {
			if (url.indexOf(open_url) >= 0) {
				// 如果是公开 地址则放行
				return invocation.invoke();
			}
		}

		// 从配置文件中获取公共访问地址
		List<String> common_urls = ResourcesUtil.gekeyList("commonURL");
		// 遍历公用 地址，如果是公用 地址则放行
		for (String common_url : common_urls) {
			if (url.indexOf(common_url) >= 0) {
				// 如果是公开 地址则放行
				return invocation.invoke();
			}
		}

		// 获取session
		HttpSession session = request.getSession();
		ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
		// 从session中取权限范围的url
		List<Permission> permissions = activeUser.getPermissions();
		for (Permission sysPermission : permissions) {
			// 权限的url
			String permission_url = sysPermission.getUrl();
			if (url.indexOf(permission_url) >= 0) {
				// 如果是权限的url 地址则放行
				return invocation.invoke();
			}
		}
		return null;
	}

}
