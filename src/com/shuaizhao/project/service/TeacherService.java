package com.shuaizhao.project.service;

import java.util.List;

import com.shuaizhao.project.exception.PasswordPatternException;
import com.shuaizhao.project.exception.UserIdHasBeenUsedException;
import com.shuaizhao.project.exception.PhonePatternException;
import com.shuaizhao.project.exception.SqlExectorException;
import com.shuaizhao.project.exception.UserNotExistException;
import com.shuaizhao.project.exception.UserPasswordNotMatchException;
import com.shuaizhao.project.model.User;
import com.shuaizhao.project.model.custom.ActiveUser;
import com.shuaizhao.project.model.custom.DataGridResult;
import com.shuaizhao.project.model.custom.SearchBean;

public interface TeacherService {
	
	public void addTeacher(User user) throws UserIdHasBeenUsedException, PasswordPatternException, PhonePatternException;
	
	public ActiveUser login(String phone,String password) throws UserPasswordNotMatchException;
	
	public boolean checkUserExist(String userId);
	
	public User findTeacherById(String UserId);
	
	public void updateTeacher(User user) throws SqlExectorException;
	
	public void deleteTeacher(User user) throws UserNotExistException;
	
	public List<User> findAllTeacher();
	
	public DataGridResult getGrid(int page,int rows,SearchBean searchBean);
	
	

}
