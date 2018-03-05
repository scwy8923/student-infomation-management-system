

function openAddStudentDialog(){
	$('#addStudentDialog').dialog('open').dialog('setTitle','New User');
	$('#addStudentForm').form('clear');
}

function openEditStudentDialog(){
	var row = $('#studentList').datagrid('getSelected');
	if (row){
		$('#editStudentDialog').dialog('open').dialog('setTitle','更新用户');
		$('#editStudentForm').form('load',row);
	}
}

function openShowStudentDialog(){
	var row = $('#studentList').datagrid('getSelected');
	if (row){
		$('#showStudentDialog').dialog('open').dialog('setTitle','查看用户');
		$('#showStudentForm').form('load',row);
	}
}

function saveStudent(){
	var row = $('#studentList').datagrid('getSelected');
	if (row){
		var url = '/StudentManagers/studentAction!updateStudent.action';
		$('#editStudentForm').form('submit',{
			url: url,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				var result = eval('(' + result + ')');
				console.info(result);
				if (result.success){
					$('#editStudentDialog').dialog('close');		// close the dialog
					$('#studentList').datagrid('reload');	// reload the user data
					
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

function searchStudent(){
	var name=$("#searchByNameBox").val();
	var userId=$("#searchByUserIdBox").val();
	$('#studentList').datagrid('load',{
		'searchBean.searchName': name,
		'searchBean.searchUserId': userId
	});

}

function addStuent(){
	url = '/StudentManagers/studentAction!addStudent.action';
	$('#addStudentForm').form('submit',{
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
				$('#addStudentDialog').dialog('close');		// close the dialog
				$('#studentList').datagrid('reload');	// reload the user data
			}
		}
	});
}

function openDeleteStudentDialog(){
	var row = $('#studentList').datagrid('getSelected');
	if (row){
		$.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(sure){
			if (sure){
				$.post('/StudentManagers/studentAction!deleteStudent.action',{userId:row.userId},function(result){
					if (result.success){
						console.info(result.success+"删除成功")
						$('#studentList').datagrid('reload');	// reload the user data
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

function openCheckStudentDialog(){
	var row = $('#studentList').datagrid('getSelected');
	if (row){
		$('#checkStudentDialog').dialog('open').dialog('setTitle','Edit User');
		$('#checkStudentForm').form('load',row);
	}
}

function openAddRoleStudentDialog() {
	var row = $('#studentList').datagrid('getSelected');
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











