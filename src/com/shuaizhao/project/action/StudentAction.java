package com.shuaizhao.project.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
import com.shuaizhao.project.exception.SqlExectorException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.custom.DataGridResult;
import com.shuaizhao.project.model.custom.SearchBean;
import com.shuaizhao.project.service.StudentService;
import com.shuaizhao.project.utils.JsonUtil;

@Namespace("/")
@ParentPackage("custom-default")
@Action("studentAction")
@Results({
	  @Result(name="studentList", type="json",params={"root","dataGridResult"}),
	  @Result(name="toList",location="/WEB-INF/jsp/studentList.jsp"),
})
@Scope("prototype")
public class StudentAction extends BaseAction implements ModelDriven<User>{

	private User user=new User();
	private static final long serialVersionUID = 1L;
	private DataGridResult dataGridResult;
	private SearchBean searchBean=new SearchBean();
	
	@Autowired
	StudentService studentService;

	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public DataGridResult getDataGridResult() {
		return dataGridResult;
	}

	public SearchBean getSearchBean() {
		return searchBean;
	}
	public void setSearchBean(SearchBean searchBean) {
		this.searchBean = searchBean;
	}
	public String deleteStudent() throws IOException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		response.setCharacterEncoding("utf-8");
		try {
			studentService.deleteStudentrById(user.getUserId());
			resultMap.put("success", "删除成功");
			response.getWriter().write(JsonUtil.toJson(resultMap));
		} catch (UserNotExistException e) {
			resultMap.put("errorMsg", "学生信息不存在");
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
	
	public String toList(){
		return "toList";
	}
	
	
	public String showStudents() throws IOException{
		try {
			dataGridResult=studentService.getGrid(page,rows,searchBean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "studentList";
	}
	
	public String updateStudent() throws IOException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		response.setCharacterEncoding("utf-8");
		try {
			studentService.updateStudent(user);
			resultMap.put("status", 200);
			resultMap.put("msg", "更新成功");
			ServletActionContext.getResponse().getWriter().write(JsonUtil.toJson(resultMap));
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			resultMap.put("status", 0);
			resultMap.put("msg", "学生信息不存在");
			ServletActionContext.getResponse().getWriter().write(JsonUtil.toJson(resultMap));
		}
		finally{
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		}
		return null;
	}
	
	public String addStudent(){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer=response.getWriter();
			studentService.insertStudent(user);
			resultMap.put("status", "200");
			resultMap.put("msg", "添加成功");
			response.getWriter().write(JsonUtil.toJson(resultMap));
			} catch (SqlExectorException e) {
				// TODO Auto-generated catch block
				resultMap.put("status", "0");
				resultMap.put("msg", "服务器正忙，请稍后再试");
				writer.write(JsonUtil.toJson(resultMap));
			}
			catch (NumberFormatException e) {
				// TODO: handle exception
				resultMap.put("status", "0");
				resultMap.put("msg", "学号格式不正确");
				writer.write(JsonUtil.toJson(resultMap));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				resultMap.put("status", "0");
				resultMap.put("msg", "服务器出错");
				e.printStackTrace();
			}
			finally{
				writer.flush();
				writer.close();
			}
		return null;
	}
	
	
}
