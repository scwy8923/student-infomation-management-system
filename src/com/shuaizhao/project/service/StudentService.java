package com.shuaizhao.project.service;

import java.util.List;

import com.shuaizhao.project.exception.SqlExectorException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.custom.DataGridResult;
import com.shuaizhao.project.model.custom.SearchBean;

public interface StudentService {

	public void insertStudent(User user) throws SqlExectorException;
	
	public List<User> findAllStudent();
	
	public void updateStudent(User user) throws UserNotExistException;
	
	public void deleteStudentrById(String id) throws UserNotExistException;
	
	public List<User> findStudent(User user);
	
	public User findStuentByUserId(String userId);
	
	public DataGridResult getGrid(int page,int rows,SearchBean searchBean);
	
}
