<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tagliburi="http://shiro.apache.org/tags" prefix="shiro" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/jsp/common_student.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
<meta charset="utf-8">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="${pageContext.request.contextPath}/js/mine/teacher.js" type="text/javascript"></script>
<SCRIPT type="text/javascript" src="<%= basePath %>js/menuload.js"></SCRIPT>
<script type="text/javascript">
	var itemAddEditor ;
	//页面初始化完毕后执行此方法
	$(function(){
		//创建富文本编辑器
		itemAddEditor = TAOTAO.createEditor("#editTeacherForm [name=desc]");
		//初始化类目选择和图片上传器
		TAOTAO.init({fun:function(node){
			//根据商品的分类id取商品 的规格模板，生成规格信息。第四天内容。
			//TAOTAO.changeItemParam(node, "itemAddForm");
		}});
	});
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#editTeacherForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
		$.post("/StudentManagers/teacherAction!updateTeacher.action",$("#editTeacherForm").serialize(), function(data){
			
			 var obj = eval('(' + data + ')');
			if(obj.status == 200){
				$.messager.alert('提示',obj.msg);
				$('#teacherList').datagrid('load');
				$('#editTeacherDialog').dialog('close');
			}
			else{
				$.messager.alert('提示',obj.msg);
			}
		});
	}
	
	function clearForm(){
		$('#editTeacherForm').form('reset');
		editTeacherForm.html('');
	}
	
	function addRole(){
		var rows = $('#roleDataGrid').datagrid('getSelections');
		if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
        		console.log(row);
    			$.post("/StudentManagers/userAction!addRole.action",
    					  {
    					    'userRole.sysUserId':$('#addRoleUserId').val(),
    					    'userRole.sysRoleId':row.id
    					  },
    					  function(data,status){
    						var result = eval('('+data+')');
    						if(result.status==200){
    							$('#addRoleDialog').dialog('close');
    							alert("添加成功");
    						}
    						else{
    							alert(result.msg);
    						}
    					    
    					  });
            }
		}
		
	}
</script>

</head>


<body>

	<table id="teacherList" title="教师管理" class="easyui-datagrid"
		style="width:100%;height:600px"
		url="${pageContext.request.contextPath}/teacherAction!showTeachers.action"
		toolbar="#toolbar,#tb" rownumbers="true" fitColumns="true"
		singleSelect="true" pagination="true">
		<thead>
			<tr>
				<th field="userId" width="50">工号</th>
				<th field="name" width="50">姓名</th>
				<th field="phone" width="50">电话</th>
				<th field="email" width="50">邮箱</th>
				<th field="age" width="50">年龄</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:3px">
		<span>通过用户名搜索:</span>
		<input id="searchByNameBox" style="line-height:26px;border:1px solid #ccc">
		<span style="width: 20sp">通过工号搜索</span>
		<input id="searchByUserIdBox" style="line-height:26px;border:1px solid #ccc">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="searchStudent()" iconCls="icon-search">搜索</a>
	</div>
	<div id="toolbar">
		
		<shiro:hasPermission name="teacher:update">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditTeacherDialog()">编辑教师</a>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="teacher:delete">
			 <a href="#"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="openDeleteTeacherDialog()">删除教师</a>
		</shiro:hasPermission>
		
			<a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="openShowTeacherDialog()">查看教师</a>
		
		<shiro:hasPermission name="user:addRole">
			<a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="openAddRoleTeacherDialog()">修改权限</a>
		</shiro:hasPermission>
	</div>


	
	<div id="editTeacherDialog" class="easyui-dialog"
		style="width:800px;height:600px;padding:10px 20px" closed="true"
		buttons="#editStudentDialog-buttons">
		<div style="padding:10px 10px 10px 10px">
	<form id="editTeacherForm" class="itemForm" method="post" enctype="multipart/form-data">
	    <table cellpadding="5">
	        <tr class="fitem">
	            <td>学号/工号:</td>
	            <td><input class="easyui-textbox" type="text" name="userId" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr >
	            <td>手机号码:</td>
	            <td><input class="easyui-textbox" name="phone" data-options="validType:'length[0,150]'" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>邮箱:</td>
	            <td><input class="easyui-textbox" type="text" name="email" data-options="required:true" />
	            	<input type="hidden" name="id"/>
	            </td>
	        </tr>
	        <tr>
	            <td>身份证号码:</td>
	            <td><input class="easyui-numberbox" type="text" name="identificationCard" data-options="required:true" /></td>
	        </tr>
	        <tr>
	            <td>性别:</td>
	            <td>
	                <input type="radio" name="sex" id="man" value="男">男</input>
                	<input type="radio" name="sex" id="woman" value="女">女</input>
	            </td>
	        </tr>
	        <tr>
	            <td>上传图片:</td>
	            <td>
	            	 <a href="javascript:void(0)" class="easyui-linkbutton picFileUpload">上传图片</a>
	                 <input type="hidden" name="headUrl"/>
	            </td>
	        </tr>
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
		</div>
	</div>
	
	<div id="showTeacherDialog" class="easyui-dialog"
		style="width:800px;height:600px;padding:10px 20px" closed="true"
		buttons="#editStudentDialog-buttons">
		<div style="padding:10px 10px 10px 10px">
	<form id="showTeacherForm" class="itemForm" method="post" enctype="multipart/form-data">
	    <table cellpadding="5">
	        <tr class="fitem">
	            <td>学号/工号:</td>
	            <td><input class="easyui-textbox" type="text" name="userId" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr >
	            <td>手机号码:</td>
	            <td><input class="easyui-textbox" name="phone" data-options="validType:'length[0,150]'" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>邮箱:</td>
	            <td><input class="easyui-textbox" type="text" name="email" data-options="required:true" />
	            	<input type="hidden" name="id"/>
	            </td>
	        </tr>
	        <tr>
	            <td>身份证号码:</td>
	            <td><input class="easyui-numberbox" type="text" name="identificationCard" data-options="required:true" /></td>
	        </tr>
	        <tr>
	            <td>性别:</td>
	            <td>
	                <input type="radio" name="sex" id="man" value="男">男</input>
                	<input type="radio" name="sex" id="woman" value="女">女</input>
	            </td>
	        </tr>
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>
		</div>
	</div>
	
	<div id="addRoleDialog" class="easyui-dialog"
		style="width:800px;height:600px;padding:10px 20px" closed="true"
		buttons="#editStudentDialog-buttons">
		<table id="roleDataGrid"></table>
		<div style="padding:10px 10px 10px 10px">
			<div style="padding:5px">
			    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addRole()">确定新增</a>
			    <input type="hidden" id="addRoleUserId"></input>
			</div>
		</div>
	</div>
	
	

</body>
</html>



