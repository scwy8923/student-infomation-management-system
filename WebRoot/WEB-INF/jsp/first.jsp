<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ tagliburi="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<head>
<title>教学管理平台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/common_js.jsp"%>
<SCRIPT type="text/javascript">
    var tabOnSelect = function(title) {
		//根据标题获取tab对象
		var currTab = $('#tabs').tabs('getTab', title);
		var iframe = $(currTab.panel('options').content);//获取标签的内容
		
		var src = iframe.attr('src');//获取iframe的src
		//当重新选中tab时将ifram的内容重新加载一遍，目的是获取当前页面的最新内容
		if (src)
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : createFrame(src)//ifram内容
				}
			});
	};
	var _menus;
	$(function() {//预加载方法
		//通过ajax请求菜单
		/* $.ajax({
			url : '${baseurl}menu.json',
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				_menus = data;
				initMenu(_menus);//解析json数据，将菜单生成
			},
			error : function(msg) {
				alert('菜单加载异常!');
			}
		}); */

		//tabClose();
		//tabCloseEven();

		$('#tabs').tabs('add', {
			title : '欢迎使用',
			content : createFrame('${baseurl}welcome.jsp')
		}).tabs({
			//当重新选中tab时将ifram的内容重新加载一遍
			onSelect : tabOnSelect
		});
		
		//修改密码
		$('#modifypwd').click(menuclick);

	});

	//退出系统方法
	function logout() {
		_confirm('您确定要退出本系统吗?',null,
				function(){
					location.href = '${baseurl}logout.action';
				}
		)
	}

	//帮助
	function showhelp(){
		$('#new_window').dialog('open').dialog('setTitle','帮助');
	}
	
	function closehelp(){
		$('#new_window').dialog('close');
	}

</SCRIPT>



<META name="GENERATOR" content="MSHTML 9.00.8112.16540">
</HEAD>

<BODY style="overflow-y: hidden;" class="easyui-layout" scroll="no" >
	<div id="new_window" class="easyui-dialog" style="width:640px;height:480px;padding:10px 20px" closed="true" buttons="#editStudentDialog-buttons">
	<p>项目人员及职责分配</p>
	<table>
	<tr><td>赵&nbsp&nbsp&nbsp帅：</td><td>登陆及权限管理</td></tr>
	<tr><td>陈莉莉：</td><td>学生信息管理</td></tr>
	<tr><td>洪玉婷：</td><td>教师信息管理</td></tr>
	<tr><td>罗&nbsp&nbsp&nbsp芳：</td><td>数据库表结构设计及云服务器配置</td></tr>
	<tr><td>朱学文：</td><td>web前端界面设计</td></tr>
	</table><br><br><br><br><br>
	<p>如果您对系统有任何不理解以及您发现了系统存在的问题，欢迎您发送邮件至areszxw@outlook.com，我们将竭诚为您服务。
	<a href="#" class="easyui-linkbutton" plain="true" onclick="closehelp()" style="position: relative;left: 420px;top: 60px;">退出帮助</a>
	</div>
	<DIV
		style='background: url("images/layout-browser-hd-bg.gif") repeat-x center 50% rgb(127, 153, 190); height: 30px; color: rgb(255, 255, 255); line-height: 20px; overflow: hidden; font-family: Verdana, 微软雅黑, 黑体;'
		border="false" split="true" region="north">
		<SPAN style="padding-right: 20px; float: right;" class="head">
			欢迎当前用户：${activeUser.username}&nbsp;&nbsp;
			<A href=javascript:showhelp()>使用帮助</A>
			&nbsp;&nbsp;
			<a title='修改密码'  href=javascript:addTab('修改密码','/StudentManagers/pageAction!toUpadtepsw.action') >修改密码</a>
			&nbsp;&nbsp;
			<A id="loginOut" href=javascript:logout()>退出系统</A>

		</SPAN> <SPAN style="padding-left: 10px; font-size: 16px;"><IMG
			align="absmiddle" src="images/blocks.gif" width="20" height="20">
			教学信息管理系统</SPAN> <SPAN style="padding-left: 15px;" id="News"></SPAN>
	</DIV>

	<DIV style="background: rgb(210, 224, 242); height: 30px;" split="false"
		region="south">

		<DIV class="footer">
			系统版本号：${version_number}&nbsp;&nbsp;&nbsp;发布日期：${version_date}
		</DIV>
	</DIV>

	<DIV style="width: 180px;" id="west" title="导航菜单" split="true"
		region="west" hide="true">

		<DIV id="nav" class="easyui-accordion" border="false" fit="true">
			
			<c:if test="${activeUser.menus!=null }">
				<div data-options="region:'west',title:'菜单',split:true" style="width:180px;">
    				<ul id="menu" class="easyui-tree" style="margin-top: 10px;margin-left: 5px;">
         			<c:forEach items="${activeUser.menus }" var="menu">
						<li>
			         		<span>
			         			<a href=javascript:addTab('${menu.name }','${baseurl }/${menu.url }')>${menu.name }</a>
			         		</span>
			         		<ul>
				         		<c:forEach items="${activeUser.permissions }" var="permission">
				         			<c:if test="${permission.parentid == menu.id }">
				         			<li data-options="attributes:{'url':'${baseurl}${permission.url}'}">
				         				<a href=javascript:addTab('${permission.name }','${baseurl }/${permission.url }')>${permission.name }</a>
				         			</li>
				         			</c:if>
				         		</c:forEach>
				         	</ul>
			         	</li>
					</c:forEach>
         			</ul>
   				 </div>
			</c:if>
		</DIV>
	</DIV>

	<DIV style="background: rgb(238, 238, 238); overflow-y: hidden;"
		id="mainPanle" region="center">
		<DIV id="tabs" class="easyui-tabs" border="false" fit="true"></DIV>
	</DIV>


</BODY>
</HTML>
