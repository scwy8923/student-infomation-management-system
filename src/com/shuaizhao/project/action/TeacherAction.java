package com.shuaizhao.project.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.opensymphony.xwork2.ModelDriven;
import com.shuaizhao.project.exception.PasswordPatternException;
import com.shuaizhao.project.exception.UserIdHasBeenUsedException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.exception.PhonePatternException;
import com.shuaizhao.project.exception.SqlExectorException;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.custom.DataGridResult;
import com.shuaizhao.project.model.custom.SearchBean;
import com.shuaizhao.project.service.TeacherService;
import com.shuaizhao.project.serviceImpl.StudentServiceImpl;
import com.shuaizhao.project.utils.JsonUtil;

@Namespace("/")
@ParentPackage("custom-default")
@Action("teacherAction")
@Results({ @Result(name = "success", type = "redirect", location = "/index.jsp"),
		@Result(name = "input_login", type = "dispatcher", location = "/WEB-INF/jsp/login.jsp"),
		@Result(name="teacherList", type="json",params={"root","dataGridResult"}),
		@Result(name = "toTeacherList", type = "dispatcher", location = "/WEB-INF/jsp/teacherList.jsp"), })
@Scope("prototype")
public class TeacherAction extends BaseAction implements ModelDriven<User> {

	private static final long serialVersionUID = 1L;

	private User user = new User();

	private DataGridResult dataGridResult = new DataGridResult();

	private SearchBean searchBean = new SearchBean();

	@Autowired
	private TeacherService teacherService;

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DataGridResult getDataGridResult() {
		return dataGridResult;
	}

	public void setDataGridResult(DataGridResult dataGridResult) {
		this.dataGridResult = dataGridResult;
	}

	public String toTeachers() {
		return "toTeacherList";
	}

	public String showTeachers() throws IOException {
		dataGridResult = teacherService.getGrid(page, rows, searchBean);
		return "teacherList";
	}

	public String addTeacher() {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		Map<String, Object> map = new HashMap<>();
		try {
			writer = response.getWriter();
			teacherService.addTeacher(user);
			map.put("status", 200);
			map.put("msg", "添加成功");
			writer.write(JsonUtil.toJson(map));
		} catch (UserIdHasBeenUsedException e) {
			// TODO Auto-generated catch block
			map.put("status", 0);
			map.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(map));

		} catch (PasswordPatternException e) {
			map.put("status", 0);
			map.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(map));

		} catch (PhonePatternException e) {
			map.put("status", 0);
			map.put("msg", e.getMessage());
			writer.write(JsonUtil.toJson(map));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return null;
	}
	
	public String deleteTeacher() throws IOException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		response.setCharacterEncoding("utf-8");
		try {
			teacherService.deleteTeacher(user);
			resultMap.put("success", "删除成功");
			response.getWriter().write(JsonUtil.toJson(resultMap));
		} catch (UserNotExistException e) {
			resultMap.put("errorMsg", "教师信息不存在");
			ServletActionContext.getResponse().getWriter().write(JsonUtil.toJson(resultMap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			resultMap.put("errorMsg", "服务器正忙，请稍后再试");
			ServletActionContext.getResponse().getWriter().write(JsonUtil.toJson(resultMap));
		}
		finally{
			
		}
		return null;
	}
	
	public String updateTeacher(){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer=response.getWriter();
			teacherService.updateTeacher(user);
			resultMap.put("status", 200);
			resultMap.put("msg", "更新成功");
			writer.write(JsonUtil.toJson(resultMap));
		} catch (SqlExectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(writer!=null){
				writer.flush();
				writer.close();
			}
			
		}
		return null;
	}

}
