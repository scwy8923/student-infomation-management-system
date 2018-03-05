<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<LINK rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/js/easyui/themes/insdep/easyui.css">
<LINK rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/js/easyui/themes/insdep/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/mine/taotao.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mine/common.js"></script>
<link href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
	<form id="studentAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr class="fitem">
	            <td>学号/工号:</td>
	            <td><input class="easyui-textbox" type="text" name="userId" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        
	        <tr class="fitem">
	            <td>姓名:</td>
	            <td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr >
	            <td>手机号码:</td>
	            <td><input class="easyui-textbox" name="phone" data-options="validType:'length[0,150]'" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>邮箱:</td>
	            <td><input class="easyui-textbox" type="text" name="email" data-options="required:true" />
	            </td>
	        </tr>
	        <tr>
	            <td>身份证号码:</td>
	            <td><input class="easyui-numberbox" type="text" name="identificationCard" data-options="required:true" /></td>
	        </tr>
	        <tr>
	            <td>性别:</td>
	            <td>
	                <input type="radio" name="sex" value="男">男</input>
                	<input type="radio" name="sex" value="女">女</input>
	            </td>
	        </tr>
	    </table>
	    
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	var itemAddEditor ;
	//页面初始化完毕后执行此方法
	$(function(){
		//创建富文本编辑器
		itemAddEditor = TAOTAO.createEditor("#studentAddForm [name=desc]");
		//初始化类目选择和图片上传器
		TAOTAO.init({fun:function(node){
			//根据商品的分类id取商品 的规格模板，生成规格信息。第四天内容。
			//TAOTAO.changeItemParam(node, "itemAddForm");
		}});
	});
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#studentAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		$.post("/StudentManagers/studentAction!addStudent.action",$("#studentAddForm").serialize(), function(data){
			var result = eval('('+data+')');
			if(result.status == 200){
				$.messager.alert('提示',result.msg);
			}
			else{
				$.messager.alert('错误',result.msg);
			}
		});
	}
	
	function clearForm(){
		$('#studentAddForm').form('reset');
		itemAddEditor.html('');
	}
</script>
