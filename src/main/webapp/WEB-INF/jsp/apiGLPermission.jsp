<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-Hant-TW">
	<head>
    	<meta name="viewport" content="width=device-width" />
	    <title>台灣企銀-日誌查詢系統權限設定</title>
	
	    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui.theme-1.12.1.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/Font-Awesome/css/all.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/overlayScrollbars/OverlayScrollbars.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/AdminLTE/adminlte-3.1.0.min.css" rel="stylesheet" />
	    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/component/jsgrid/jsgrid.min.css" />
	    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/component/jsgrid/jsgrid-theme.min.css" />
	    <link rel="stylesheet" href="<%=request.getContextPath()%>/component/CSS/style.css">
	
	</head>
	<body class="sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed sidebar-collapseX sidebar-collapse" style="height: auto;">
		<div class="wrapper">
		     <!-- Header -->
	        <nav class="main-header navbar navbar-expand navbar-white navbar-light">
	            <%@ include file="header.jsp" %>
	        </nav>
	
	        <!-- 主選單 -->
	        <aside class="main-sidebar sidebar-dark-primary elevation-4">
	           <%@ include file="navBar.jsp" %>
	        </aside>
	
		    <div class="content-wrapper">
		        <div class="main-content">
		            <div class="main-title">
		                <span class="title-text" >權限設定</span>
		            </div>
					<form id="RoleForm" class="controlhidden">
			            <div class="search-block">
				            <div class="search-block-line">
			                        <label>
			                            <span class="text">索　　引：</span>
			                            <input type="text" id="apiglRoleId" name="apiglRoleId" value="${apiglrole.apiglRoleId}" disabled="true">
			                        </label>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text" >編　　號：</span>
			                            <input type="text" id="apiglRoleNumber" name="apiglRoleNumber" value="${apiglrole.apiglRoleNumber}" disabled="true">
			                        </label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text" >名　　稱：</span>
			                            <input type="text" id="apiglRoleName" name="apiglRoleName" value="${apiglrole.apiglRoleName}" disabled="true">
			                        </label>
			                    </div>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text" >說　　明：</span>
			                            <input type="text" id="apiglRoleDirections" name="apiglRoleDirections" value="${apiglrole.apiglRoleDirections}" disabled="true">
			                        </label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text" >帳號種類：</span>
			                            <select id="apiglRoleType" name="apiglRoleType" disabled="true">
										    <option value="1">總行群組</option>
										    <option value="2">分行群組</option>
										    <option value="3">資訊部群組</option>
										    <option value="4">分行OTP發卡行</option>
										</select>
			                        </label>
			                    </div>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text">啟　　用：</span>
			                            <select id="enabled" name="enabled" disabled="true">
										    <option value="0">未啟用</option>
										    <option value="1">啟用</option>
										</select>
			                        </label>
			                    </div>
			                </div>
			            </div>
		            </form>
					<form id="userForm" class="controlhidden">
			            <div class="search-block">
				            <div class="search-block-line">
		                       	<label>
		                            <span class="text" >索　　引：</span>
		                            <input type="text" id="userId" value="${user.userId}" disabled="true">
		                        </label>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text" >帳號名稱：</span>
			                            <input type="text" id="account" value="${user.account}" disabled="true">
		                        	</label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text" >帳號序號：</span>
			                            <input type="text" id="accountId" value="${user.accountId}" disabled="true">
			                        </label>
			                    </div>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text" >姓　　名：</span>
			                            <input type="text" id="name" value="${user.name}" disabled="true">
			                        </label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text" >群組名稱：</span>
			                             <input type="text" id="groupName" value="${user.groupName}" disabled="true">         
			                        </label>
			                    </div>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                    	<label>
			                            <span class="text">分行/部門代碼：</span>
			                             <input type="text" id="branch" value="${user.branch}" disabled="true">
			                        </label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text">啟　　用：</span>
			                            <select id="userenabled" disabled="true">
										    <option value="0">未啟用</option>
										    <option value="1">啟用</option>
										</select>
			                        </label>
			                    </div>
			                </div>
			            </div>
		            </form>
					
		            <form id="myForm" name="myForm" method="POST" action="<%=request.getContextPath()%>/apiGLPermission/get">
			            <div class="search-block mt-3">
			            
			                <table class="permtable mt-3" id="myTable" name="myTable">
			                	<thead>
									<tr>
										<th width=10%>序號</th><th width=30%>功能</th><th width=10%>啟用</th>
										<th width=10%>序號</th><th width=30%>功能</th><th width=10%>啟用</th>
									</tr>
								</thead>
				                <tbody id="formvalue"></tbody>
			                </table>
			                <div class="search-block-line btnventer">
			                	<div class="btn searchbtn newbtn-gray" onclick="doreturn();">返回</div>
			                    &nbsp;&nbsp;
			                    <div class="btn searchbtn newbtn-yellow" onclick="doupdate();">修改</div>
			                </div>
			            </div>
		            </form>
		        </div>
		    </div>
			
			<%@ include file="util.jsp" %>
	
		    <!-- 頁尾 -->
	        <footer class="main-footer">
	           <%@ include file="footer.jsp" %>
	        </footer>
		</div>
	
		<script src="<%=request.getContextPath()%>/component/jQuery/jquery-3.6.0.min.js"></script>
		<script src="<%=request.getContextPath()%>/component/jQuery/DateTimePicker/jquery.datetimepicker-2.5.20.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.bundle.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/overlayScrollbars/jquery.overlayScrollbars.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/AdminLTE/adminlte-3.1.0.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/jsgrid/jsgrid.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/JS/all.js"></script>
		<script src="<%=request.getContextPath()%>/component/JS/apiGLUtil.js"></script>
				
		<script>

			var func;
			if(${apiglrole.apiglRoleId} != -1) {
			    $("#enabled").val('${apiglrole.enabled}');
			    document.getElementById("formvalue").innerHTML = '${functionsTable}';
			    document.getElementById("selectApiglRole").innerHTML = '<i class="fa-solid fa-circle nav-icon"></i>';
			    document.getElementById("RoleForm").style.display = 'block';
			    func = 0;
			} else {
				$("#userenabled").val('${user.enabled}');
				document.getElementById("formvalue").innerHTML = '${functionsTable}';
				document.getElementById("selectUserPermission").innerHTML = '<i class="fa-solid fa-circle nav-icon"></i>';
				document.getElementById("userForm").style.display = 'block';
				func = 1;
			}
		    
			function getTableData() {
				let arrData=[];  
				$("#myTable tr").each(function(){
				    let currentRow=$(this);
				    let apiglroleid = '${apiglrole.apiglRoleId}';
				    let userid = '${user.userId}';
				    let col1_apiglfunctionid = currentRow.find("td:eq(0)").text();
				    let col1_apiglfunctionname = currentRow.find("td:eq(1)").text();
				    let col1_permissionenabled = 0;
				    let col1permval = currentRow.find("td:eq(2) input:checkbox");  
				    if($(col1permval).prop('checked')) {
				    	col1_permissionenabled = 1;
				    }
				    if(col1_apiglfunctionid != '') {
				    	let obj={};
				    	if(func == 0) {
				    		obj.apiglRoleId = apiglroleid;
				    	} else {
				    		obj.userId = userid;
				    	}
					    obj.apiglFunctionId = col1_apiglfunctionid;
					    obj.enabled = col1_permissionenabled ;
					    arrData.push(obj);
				    }
				    
				    let col2_apiglfunctionid = currentRow.find("td:eq(3)").text();
				    let col2_apiglfunctionname = currentRow.find("td:eq(4)").text();
				    let col2_permissionenabled = 0;
				    let col2permval = currentRow.find("td:eq(5) input:checkbox");  
				    if($(col2permval).prop('checked')) {
				    	col2_permissionenabled = 1;
				    }
				    if(col2_apiglfunctionid != '') {
				    	let obj={};
				    	if(func == 0) {
				    		obj.apiglRoleId = apiglroleid;
				    	} else {
				    		obj.userId = userid;
				    	}
					    obj.apiglFunctionId = col2_apiglfunctionid;
					    obj.enabled = col2_permissionenabled ;
					    arrData.push(obj);
				    }
				});
			  return arrData;
			  
			}

			//錯誤訊息顯示
			function errorMsg(title, msg) {
				document.getElementById("myModalLabel").innerHTML = title;
				document.getElementById("myModalBoby").innerHTML = msg;
				document.getElementById("btnModal").click();
			}
			
			function doupdate() {
				var path = '${path}';
				$.ajax({
                    url: '<%=request.getContextPath()%>/apiGLPermission/' + path + '/update',
                    dataType: "json",
        		    contentType:"application/json; charset=utf-8",
                    type: 'POST',
                    data: JSON.stringify(getTableData()),
                    error: function(error) {
                    	errorMsg("錯誤", error.responseJSON.message);
                    },
                    success: function(data) {
                    	console.log(data);
                    	errorMsg("結果", data.message);
                    }
                })
			}
			
			function doreturn() {
				history.back();
			}
		</script>
	</body>
</html>