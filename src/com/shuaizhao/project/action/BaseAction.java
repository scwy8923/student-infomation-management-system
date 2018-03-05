/**
 * 
 */
package com.shuaizhao.project.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 *<p>Title:BaseAction </p>
 *<p>Description: </p>
 * @author forever
 * @date 2017年5月24日
*/
public class BaseAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware,SessionAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HttpServletRequest request;
	public HttpServletResponse response;
	public Map<String, Object> session;
	
	int page;
	
	int rows;
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response=response;
	}
		

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
