package com.shuaizhao.project.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ModelDriven;
import com.shuaizhao.project.exception.RoleNotExistException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.exception.UserPasswordNotMatchException;
import com.shuaizhao.project.exception.UserRoleExistException;
import com.shuaizhao.project.model.Role;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.UserRole;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.service.UserService;
import com.shuaizhao.project.utils.GloableConstance;
import com.shuaizhao.project.utils.JsonUtil;

@Namespace("/")
@ParentPackage("custom-default")
@Action("userAction")
@Results({
	  @Result(name="roleMessage", type="json",params={"root","roles"}),
	  @Result(name="login",location="/WEB-INF/jsp/login.jsp"),
	  @Result(name="user_update",location="/WEB-INF/jsp/user-update.jsp"),
	  @Result(name="userInfo",type="dispatcher",location="/WEB-INF/jsp/userInfo.jsp"),
})
@Scope("prototype")
public class UserAction extends BaseAction implements ModelDriven<User>{
	
	/**
	 * 
	 */
	
	@Autowired
	UserService userService;
	private static final long serialVersionUID = 1L;
	
	private List<Role> roles;

	private User user=new User();
	
	private File pictures;
	
	private String picturesFileName;
	
	private UserRole userRole=new UserRole();
	
	private String oldPassword;
	
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public File getPictures() {
		return pictures;
	}

	public void setPictures(File pictures) {
		this.pictures = pictures;
	}

	public String getPicturesFileName() {
		return picturesFileName;
	}

	public void setPicturesFileName(String picturesFileName) {
		this.picturesFileName = picturesFileName;
	}

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	public String login() throws Exception{
		//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
				String exceptionClassName = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
				//根据shiro返回的异常类路径判断，抛出指定异常信息
				if(exceptionClassName!=null){
					if (UnknownAccountException.class.getName().equals(exceptionClassName)||AuthenticationException.class.getName().equals(exceptionClassName)) {
						//最终会抛给异常处理器
						request.setAttribute("message_login", "账号不存在");
					} else if (IncorrectCredentialsException.class.getName().equals(
							exceptionClassName)) {
						request.setAttribute("message_login", "用户名或密码错误");
					} else if("randomCodeError".equals(exceptionClassName)){
						request.setAttribute("message_login", "验证码错误 ");
					}else {
						
						request.setAttribute("message_login", "用户名或密码错误");//最终在异常处理器生成未知错误
					}
				}
				//此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
				//登陆失败还到login页面
				return "login";
	}
	
	public String toUpdateUser(){
		request.setAttribute("tempUser", user);
		return "user_update";
	}
	
	public String uploadPicture() throws IOException {
		String uploadUrl=request.getServletContext().getRealPath("/")+"upload";
		System.out.println(uploadUrl);
		File path=new File(uploadUrl);
		if(!path.exists()){
			path.mkdirs();
		}
		StringBuilder urlPath = new StringBuilder();
		try {
				String fileName=System.currentTimeMillis()+picturesFileName.substring(picturesFileName.lastIndexOf('.'));
				File temp=new File(path,fileName);
				FileUtils.copyFile(pictures, temp);
				urlPath.append(GloableConstance.BASE_URL+"/upload/"+fileName);
		} catch (IOException e) {
			Map<String, Object> map=new HashMap<>();
			map.put("error", 1);
			map.put("message", "上传错误");	
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(JsonUtil.toJson(map));
		}
		Map<String, Object> map=new HashMap<>();
		map.put("error", 0);
		map.put("url", urlPath);	
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(JsonUtil.toJson(map));
		return null;
	}
	
	public String userInfo(){
		ActiveUser activeUser = (ActiveUser) session.get("activeUser");
		User user = userService.findUserById(activeUser.getUserid());
		if(user.getHeadUrl()!=null){
			String []urls=user.getHeadUrl().split(",");
			user.setUrlList(Arrays.asList(urls));
		}		
		request.setAttribute("userInfo", user);
		return "userInfo";
	}
	
	public String getRoleList(){
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter writer = response.getWriter();
			roles = userService.findRoles(user.getUserId());
			writer.write(JsonUtil.toJson(roles));
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String addRole(){
		response.setCharacterEncoding("utf-8");
		Map<String,Object> result=new HashMap<>();
		PrintWriter writer=null;
		try {
			writer = response.getWriter();
			userService.addRole(userRole.getSysUserId(), userRole.getSysRoleId());
			result.put("status", 200);
			result.put("msg", "添加成功");
			writer.write(JsonUtil.toJson(result));
		} catch (UserRoleExistException e) {
			// TODO Auto-generated catch block
			result.put("status", 0);
			result.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(result));
		} catch (UserNotExistException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(result));
		} catch (RoleNotExistException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(result));
		} catch (IOException e) {
			
		}
		return null;
	}
	
	public String  updatePassword(){
		ActiveUser user=(ActiveUser) session.get("activeUser");
		response.setCharacterEncoding("utf-8");
		Map<String,Object> result=new HashMap<>();
		PrintWriter writer=null;
		try {
			writer = response.getWriter();
			userService.updatePassword(user.getUserid(), oldPassword, newPassword);
			result.put("status", 200);
			result.put("msg", "修改成功");
			writer.write(JsonUtil.toJson(result));
		} catch (UserPasswordNotMatchException e) {
			result.put("status", 0);
			result.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(result));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
