<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ page language="java" import="com.shuaizhao.project.model.User" %>
<%@ page language="java" import="java.lang.*" %>
<%@ page language="java" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/easyui/themes/insdep/easyui.css">
<LINK rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/easyui/themes/insdep/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/mine/taotao.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/mine/common.js"></script>
<link href="${pageContext.request.contextPath}/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body onload="load()">
	
	<script type="text/javascript">
		function load()
		{
			var radio = document.getElementsByName("sex");
			var sex="${userInfo.sex }";
			if(sex=="男") radio[0].checked=true;
			else radio[1].checked=true;
		}
	</script>
	
	<form name="form" id="editUserForm" class="itemForm" method="post" enctype="multipart/form-data">
	    <table cellpadding="5">
	        <tr class="fitem">
	            <td>学号/工号:</td>
	            <td><input class="easyui-textbox" type="text" value="${userInfo.userId }" name="userId"  data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr >
	            <td>手机号码:</td>
	            <td><input class="easyui-textbox" value="${userInfo.phone }" name="phone" data-options="validType:'length[0,150]'" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>邮箱:</td>
	            <td><input class="easyui-textbox" type="text" value="${userInfo.email }" name="email" data-options="required:true" > </input>
	            	<input type="hidden" name="id"/>
	            </td>
	        </tr>
	        <tr>
	            <td>身份证号码:</td>
	            <td><input class="easyui-numberbox" type="text" value="${userInfo.identificationCard }" name="identificationCard" data-options="required:true" > </input></td>
	        </tr>
	        <tr>
	            <td>性别:</td>
	            <td>
	                <!-- <input class="easyui-textbox" type="text" value="${userInfo.sex }" name="sex" data-options="validType:'length[1,30]'" ></input>
	                 -->
	                <input type="radio" name="sex" id="man" value="男">男</input>
                	<input type="radio" name="sex" id="woman" value="女">女</input>
	            </td>
	        </tr>
	        <tr>
	            <td>图片信息</td>
	            <c:forEach items="${userInfo.urlList}" var="url">
	            	<td><img  src="${url}"  alt="个人信息" /></td>
	            </c:forEach>
	        </tr>
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>
</body>
</html>