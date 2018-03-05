/**
 * 
 */
package com.shuaizhao.project.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.annotation.Scope;

import com.shuaizhao.project.model.Permission;
import com.shuaizhao.project.model.custom.ActiveUser;

/**
 *<p>Title:PageAction </p>
 *<p>Description: </p>
 * @author forever
 * @date 2017年6月1日
*/

@Namespace("/")
@ParentPackage("custom-default")
@Action("pageAction")
@Results({
	  @Result(name="first",type="dispatcher",location="/WEB-INF/jsp/first.jsp"),
	  @Result(name="studentAdd",type="dispatcher",location="/WEB-INF/jsp/student-add.jsp"),
	  @Result(name="teacherAdd",type="dispatcher",location="/WEB-INF/jsp/teacher-add.jsp"),
	  @Result(name="toUpadtepsw",type="dispatcher",location="/WEB-INF/jsp/modifypwd.jsp"),
})
@Scope("prototype")
public class PageAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String first(){
		Subject currentUser = SecurityUtils.getSubject();
		ActiveUser user = (ActiveUser) currentUser.getPrincipal();
		session.put("activeUser", user);
		return "first";
	}
	
	public String studentAdd(){
		return "studentAdd";
	}
	
	public String teacherAdd(){
		return "teacherAdd";
	}
	
	public String studentList(){
		return "first";
	}
	
	public String toUpadtepsw(){
		return "toUpadtepsw";
	}
	
	
}
