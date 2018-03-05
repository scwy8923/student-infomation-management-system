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
	<form id="modifypwd" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr class="fitem">
	            <td>原密码：</td>
	            <td><input class="easyui-textbox" type="password" name="oldPassword" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        
	        <tr class="fitem">
	            <td>新密码</td>
	            <td><input class="easyui-textbox" type="password" name="newPassword" id="newPwd1" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr class="fitem">
	            <td>确认密码</td>
	            <td><input class="easyui-textbox" type="password" name="newPassword2" id="newPwd2" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	    </table>
	    
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>
<script type="text/javascript">
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#modifypwd').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		
		if($('#newPassword').val()!=$('#newPassword2').val()){
			$.messager.alert('提示','两次新密码需要一致!');
			return;
		}

		$.post("/StudentManagers/userAction!updatePassword.action",$("#modifypwd").serialize(), function(data){
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
		$('#modifypwd').form('reset');
		itemAddEditor.html('');
	}
</script>
