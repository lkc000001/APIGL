<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="zh-Hant-TW">
	<head>
    	<meta name="viewport" content="width=device-width" />
	    <title>台灣企銀-日誌查詢系統角色管理</title>
	
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
		                <span class="title-text" id="showtitle">角色管理</span>
		            </div>
					<!-- 搜尋bar -->
		            <form id="myForm" name="myForm">
			            <div class="search-block">
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text" >編　　號：</span>
			                            <input type="text" id="apiglRoleNumber" name="apiglRoleNumber">
			                        </label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text" >名　　稱：</span>
			                            <input type="text" id="apiglRoleName" name="apiglRoleName">
			                        </label>
			                    </div>
			                </div>
			                <div class="search-block-line">
			                    <div>
			                        <label>
			                            <span class="text" >說　　明：</span>
			                            <input type="text" id="apiglRoleDirections" name="apiglRoleDirections">
			                        </label>
			                        &nbsp;&nbsp;&nbsp;
			                        <label>
			                            <span class="text" >帳號種類：</span>
			                            <select id="apiglRoleType" name="apiglRoleType">
										    <option value="" selected></option>
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
			                            <select id="enabled" name="enabled">
										    <option value="" selected></option>
										    <option value="0">未啟用</option>
										    <option value="1">啟用</option>
										</select>
			                        </label>
			                    </div>
			                </div>
			                
			                <div class="search-block-line btnventer">
			                    <div class="btn searchbtn newbtn-gray" onclick="doClear(0);">清除</div>
			                    &nbsp;&nbsp;
			                    <div class="btn searchbtn newbtn-yellow" onclick="doquery();">搜尋</div>
			                    &nbsp;&nbsp;
			                    <div class="btn searchbtn newbtn-blue" data-bs-toggle="modal" data-bs-target="#updateModal" id="btnupdate" onclick="doUpdateModal();">新增</div>
			                </div>
			            </div>
		            </form>
		            
					<%@ include file="jsgridtop.jsp" %>

		            <div id="jsGrid"></div>
		
		        </div>
		    </div>
		
			<div class="modal fade" id="updateModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="updateModalTitle"></h4>
						</div>
						<div class="wordWrap modal-body" id="addDataDiv">
							<form id="updateForm" name="updateForm" method="POST" action="#">
					            <div class="search-block">
					            	<div class="search-block-line controlhidden" id="apiglRoleIddvi" >
				                        <label>
				                            <span class="text" >索　　引：</span>
				                            <input type="text" class="modal-readonly" id="updateRoleId" name="apiglRoleId" readonly="readonly">
				                        </label>
					                </div>
					                <div class="search-block-line">
				                        <label>
				                            <span class="text" >帳號種類：</span>
				                             <select id="updateRoleType" name="apiglRoleType">
											    <option value="1" selected>總行群組</option>
											    <option value="2">分行群組</option>
											    <option value="3">資訊部群組</option>
											    <option value="4">分行OTP發卡行</option>
											</select>
				                        </label>
					                </div>
					                <div class="search-block-line">
					                        <label>
					                            <span class="text">角色編號：</span>
					                            <input type="text" id="updateRoleNumber" name="apiglRoleNumber">
					                        </label>
					                </div>
					                <div class="search-block-line">
					                    <div>
					                        <label>
					                            <span class="text">角色名稱：</span>
					                            <input type="text" class="modal-readonly" id="updateRoleName" name="apiglRoleName" readonly="readonly">
					                        </label>
					                    </div>
					                </div>
					                <div class="search-block-line">
					                    <div>
					                        <label>
					                            <span class="text">角色說明：</span>
					                            <input type="text" id="updateRoleDirections" name="apiglRoleDirections">
					                        </label>
					                    </div>
					                </div>
					                <div class="search-block-line">
					                    <div>
					                        <label>
					                            <span class="text">啟　　用：</span>
					                            <input type="checkbox" id="updateEnabled" name="enabled" onclick="setvalue();">
					                        </label>
					                    </div>
					                </div>
					            </div>
							</form>
							<div class="error" id="error"></div>
						</div>
						<div class="wordWrap modal-body controlhidden" id="deleteConfirmDiv">
							<h4 id="deleteConfirmBoby"></h4>
						</div>
						<div class="modal-footer">
						 	<button type="button" class="btn newbtn-gray" data-bs-dismiss="modal">關閉</button>
						 	&nbsp;&nbsp;
							<button type="button" id="showdelete" class="btn modal-delete-btn" onclick="doShowConfirm();">刪除</button>
							&nbsp;&nbsp;
							<button type="button" id="saveConfirmbtn" class="btn btn-primary" onclick="dosave();">儲存</button>
							
						</div>
					</div>
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
			var clicknum = 0;
			var itemsCount = 0;
		    var checkClear = 0;
		    document.getElementById("selectApiglRole").innerHTML = '<i class="fa-solid fa-circle nav-icon"></i>';
		    document.getElementById("showtitle").innerHTML = document.getElementById("showApiglRole").innerHTML;
		    
			function dosave() {
				let roleId = document.getElementById("updateRoleId").value;
				let methodType = 'POST';

				if(checkData()){
					if(roleId == '') {
						methodType = 'POST';
					} else {
						methodType = 'PUT';
					}
					$.ajax({
	                    url: '<%=request.getContextPath()%>/apiGLRole/',
	                    type: methodType,
	                    dataType: "json",
	        		    contentType:"application/json; charset=utf-8",
	                    data: JSON.stringify(objectifyForm($("#updateForm").serializeArray())),
	                    success: function(data) {
	                    	showMsg("", data.message);
	                    	doClearData();
	                    },
	                    error: function(error) {
	                    	showMsg("錯誤", error.responseJSON.message);
	                    	doClearData();
	                    }
	                })
				}
			}
			
			function dodelete() {
				let updateRoleId = document.getElementById("updateRoleId").value;
           		$.ajax({
                       url: '<%=request.getContextPath()%>/apiGLRole/delete',
                       type: 'DELETE',
                       data: {
                           "id": updateRoleId
                       },
                       success: function(data) {
                    	   	showMsg("刪除結果", "刪除成功");
                       		doClearData();
                       },
                       error: function(error) {
                    	   	showMsg("錯誤", error.responseJSON.message);
                       		doClearData();
                       }
                   })
			}
			
			//查詢資料
			function doquery() {
				let showcount = document.getElementById("showcount").value;
				clicknum = 0;
				$("#jsGrid").jsGrid("reset");
				dojsGrid('queryapiGLRole', showcount);
			}
			
			var selectEnabled = [
		        { Name: "未啟用", Id: "0" },
		        { Name: "啟用", Id: "1" }
		    ];
			var selectRoleType = [
					{ Name: "總行群組", Id: "1" },
					{ Name: "分行群組", Id: "2" },
					{ Name: "資訊部群組", Id: "3" },
					{ Name: "分行OTP發卡行", Id: "4" }
				];
			//顯示查詢結果
			function dojsGrid(apiurl, showcount) {
				
				let message = "無資料";
				
				$("#jsGrid").jsGrid({
				    width: "100%",
				    height: "auto",
		
				    inserting: false,
				    editing: false,
				    sorting: true,
				    paging: true,
				    
				    autoload: true,
				    
				    pageLoading: true,
				    pageIndex: 1,
				    pageSize: showcount,
				    pageButtonCount: 5,
				    pagerFormat: "頁數: {first} {prev} {pages} {next} {last} &nbsp;&nbsp; {pageIndex} / {pageCount}",
				    pagePrevText: "上一頁",
				    pageNextText: "下一頁",
				    pageFirstText: "第一頁",
				    pageLastText: "最後頁",
				    
				    noDataContent: message,
				    controller:
					{
					    loadData: function(filter) {
					    	if(checkClear != 1){
						        var requ = Object.assign(filter, objectifyForm($("#myForm").serializeArray()));
						        return queryajax("/apiGLRole/" + apiurl, requ, "<%=request.getContextPath()%>");
						    }
					    }
					},
					fields: [
						{ name: "apiglRoleId", type: "text", width: 40 , title:"索引" },
						{ name: "apiglRoleNumber", type: "text", width: 40, title: "編號(營運套)"},
						{ name: "apiglRoleName", type: "text", width: 60, title:"名稱" },
						{ name: "apiglRoleDirections", type: "text", width: 160, title:"說明"},
						{ name: "apiglRoleType", type: "select", items: selectRoleType, valueField: "Id", textField: "Name", width: 100, title:"帳號種類"},
						{ name: "enabled", type: "select", items: selectEnabled, valueField: "Id", textField: "Name", width: 60, title:"狀態", sorting: false },
						{ 
							itemTemplate: function(_, item) {
				                return $('<button class="update-btn-color">').text("編輯")
				                	.on("click", function() {
				                		document.getElementById("queryId").value = item.apiglRoleId;
				        				document.getElementById("updateModalTitle").innerHTML = '修改角色資料';
				                		document.getElementById("apiglRoleIddvi").style.display = 'block';
				                		document.getElementById("showdelete").style.display = 'block';
				                		document.getElementById("updateRoleName").className = 'modal-readonly';
				                		document.getElementById("updateRoleName").readOnly = true;
				                		$.ajax({
				                		    type: "GET",
				                		    url: "<%=request.getContextPath()%>/apiGLRole/findByApiglRoleId/" + item.apiglRoleId,
				                		    success: function (data) {
				                		    	document.getElementById("updateRoleId").value = data["apiglRoleId"];
						                		document.getElementById("updateRoleType").value = data["apiglRoleType"];
						                		document.getElementById("updateRoleNumber").value = data["apiglRoleNumber"];
						                		document.getElementById("updateRoleName").value = data["apiglRoleName"];
						                		document.getElementById("updateRoleDirections").value = data["apiglRoleDirections"];
						                		document.getElementById("updateEnabled").value = data["enabled"];
						                		document.getElementById("updateEnabled").checked = (data["enabled"] == '1' ? true : false);
				                		    }
				                	    });
				                		$('#updateModal').modal('show');
				                	});
				          	}, sorting: false, width: 60
						},
						{
							itemTemplate: function(_, item) {
				                return $('<button class="perm-btn-color">').text("權限")
				                	.on("click", function() {
				                		window.location.href="<%=request.getContextPath()%>/apiGLPermission/role/" + item.apiglRoleId;
				                	});
				          	}, sorting: false, width: 60
						}
					]
				})
			}
		
			//清除查詢資料
			function doClear(num) {
				checkClear = 1;
				$("#jsGrid").jsGrid("reset");
				if (num == 0) {
					doClearData()
				}
				$("#jsGrid").children().remove();
				checkClear = 0;
			}
		
			function doClearData() {
				document.getElementById("itemsCountDiv").style.display = 'none';
				document.getElementById("apiglRoleNumber").value = null;
				document.getElementById("apiglRoleName").value = null;
				document.getElementById("apiglRoleDirections").value = null;
				document.getElementById("apiglRoleType").value = null;
				document.getElementById("enabled").value = null;
				document.getElementById("updateRoleId").value = null;
				document.getElementById("updateRoleType").value = "1";
				document.getElementById("updateRoleNumber").value = null;
				document.getElementById("updateRoleName").value = null;
				document.getElementById("updateRoleDirections").value = null;
				document.getElementById("updateEnabled").value = "0";
				document.getElementById("updateEnabled").checked = false;
			}
			
			//錯誤訊息顯示
			function errorMsg(title, msg) {
				if(clicknum == 0) {
					document.getElementById("myModalLabel").innerHTML = title;
					document.getElementById("myModalBoby").innerHTML = msg;
					document.getElementById("btnModal").click();
					doClear(1);
					clicknum = 1;
				}
			}
			
			function doUpdateModal() {
				doClearData();
				document.getElementById("error").innerHTML = "";
				document.getElementById("updateModalTitle").innerHTML = '新增角色資料';
				document.getElementById("apiglRoleIddvi").style.display = 'none';
				document.getElementById("showdelete").style.display = 'none';
				document.getElementById("updateRoleName").className = '';
				document.getElementById("updateRoleName").readOnly = false;
				
			}
			
			function checkData() {
				if (document.getElementById("updateRoleName").value == '') {
					document.getElementById("error").innerHTML = "角色名稱不能為空白";
					return false;
				}
				return true;
			}
			
			function setvalue() {
				let checkvalue = document.getElementById("updateEnabled").value;
				document.getElementById("updateEnabled").value = checkvalue == 0 ? 1 : 0;
			}
			
			function doShowConfirm() {
				document.getElementById("deleteModalTitle").innerHTML = '刪除功能資料';
        		document.getElementById("deleteModalBoby").innerHTML = '是否確認刪除';
        		$('#deleteModal').modal('show');
			}
			
			function showMsg(title, msg) {
				document.getElementById("myModalLabel").innerHTML = title;
				document.getElementById("myModalBoby").innerHTML = msg;
				document.getElementById("btnModal").click();
				doClear(1);
			}
			
		</script>
	</body>
</html>