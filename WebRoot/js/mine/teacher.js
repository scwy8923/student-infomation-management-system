

function openAddTeacherDialog(){
	$('#addTeacherDialog').dialog('open').dialog('setTitle','New User');
	$('#addTeacherForm').form('clear');
}

function openEditTeacherDialog(){
	var row = $('#teacherList').datagrid('getSelected');
	if (row){
		$('#editTeacherDialog').dialog('open').dialog('setTitle','更新用户');
		$('#editTeacherForm').form('load',row);
	}
}

function openShowTeacherDialog(){
	var row = $('#teacherList').datagrid('getSelected');
	if (row){
		$('#showTeacherDialog').dialog('open').dialog('setTitle','查看');
		$('#showTeacherForm').form('load',row);
	}
}

function saveTeacher(){
	var row = $('#TeacherList').datagrid('getSelected');
	if (row){
		var url = '/TeacherManagers/TeacherAction!updateTeacher.action';
		$('#editTeacherForm').form('submit',{
			url: url,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var result = eval('(' + result + ')');
				console.info(result);
				if (result.success){
					$('#editTeacherDialog').dialog('close');		// close the dialog
					$('#TeacherList').datagrid('reload');	// reload the user data
					
				} else {
					$.messager.show({
						title: 'Error',
						msg: result.errorMsg
					});
				}
			}
		});
	}
}

function searchTeacher(){
	var name=$("#searchByNameBox").val();
	var userId=$("#searchByUserIdBox").val();
	$('#TeacherList').datagrid('load',{
		'searchBean.searchName': name,
		'searchBean.searchUserId': userId
	});

}

function addStuent(){
	url = '/TeacherManagers/TeacherAction!addTeacher.action';
	$('#addTeacherForm').form('submit',{
		url: url,
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			console.info(result)
			var result = eval('('+result+')');
			if (result.errorMsg){
				$.messager.show({
					title: 'Error',
					msg: result.errorMsg
				});
			} else {
				$('#addTeacherDialog').dialog('close');		// close the dialog
				$('#TeacherList').datagrid('reload');	// reload the user data
			}
		}
	});
}

function openDeleteTeacherDialog(){
	var row = $('#teacherList').datagrid('getSelected');
	if (row){
		$.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(sure){
			if (sure){
				$.post('/teacherManagers/teacherAction!deleteTeacher.action',{id:row.id},function(result){
					if (result.success){
						console.info(result.success+"删除成功")
						$('#TeacherList').datagrid('reload');	// reload the user data
					} else {
						console.info(result.success+"删除失败")
						$.messager.show({	// show error message
							title: 'Error',
							msg: result.errorMsg
						});
					}
				},'json');
			}
		});
	}
}

function openCheckTeacherDialog(){
	var row = $('#TeacherList').datagrid('getSelected');
	if (row){
		$('#checkTeacherDialog').dialog('open').dialog('setTitle','Edit User');
		$('#checkTeacherForm').form('load',row);
	}
}

function openAddRoleTeacherDialog() {
	var row = $('#teacherList').datagrid('getSelected');
	$('#addRoleUserId').val(row.userId);
	$('#roleDataGrid').datagrid({
    	pagination:true,
    	fitColumns:true,
        url:'/StudentManagers/userAction!getRoleList.action?userId='+row.userId,
        columns:[[
            {field: 'ck', checkbox: true, align: 'left', width: 50, height: 40 },
            {field:'id',title:'权限id',width:100},
            {field:'name',title:'权限名',width:100,align:'right'}
        ]]
    });
	
	$('#addRoleDialog').dialog("open").dialog('setTitle','修改权限');
}












