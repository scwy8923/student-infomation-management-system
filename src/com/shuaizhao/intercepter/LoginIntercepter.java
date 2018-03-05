/**
 * 
 */
package com.shuaizhao.intercepter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.utils.ResourcesUtil;

/**
 *<p>Title:LoginIntercepter </p>
 *<p>Description: </p>
 * @author forever
 * @date 2017年5月24日
*/
public class LoginIntercepter extends MethodFilterInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Log log = LogFactory.getLog(LoginIntercepter.class);

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ActiveUser user = (ActiveUser) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer url = request.getRequestURL();
		List<String> open_urls = ResourcesUtil.gekeyList("anonymousURL");
		//遍历公开 地址，如果是公开 地址则放行
		for(String open_url:open_urls){
			if(url.indexOf(open_url)>=0){
				//如果是公开 地址则放行
				return invocation.invoke();
				
			}
		}
		if (user == null) {
			ActionSupport action = (ActionSupport) invocation.getAction(); // 得到当前拦截的action对象。

			action.addActionError("权限不足，请先登录");// 存储错误信息

			return Action.LOGIN;
		}

		return invocation.invoke();
	}

}
