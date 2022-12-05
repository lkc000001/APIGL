<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="com.ApiGL.entity.request.ConditionsRequest" %>

<!DOCTYPE html>
<html>
	<head>
	    <meta name="viewport" content="width=device-width" />
	    <title>台灣企銀-日誌查詢系統設定</title>
	
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
	<body class="sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed sidebar-collapseX" style="height: auto;">
		<div class="wrapper">
		     <!-- Header -->
			<nav class="main-header navbar navbar-expand navbar-white navbar-light">
				<%@ include file="header.jsp" %>
			</nav>
			
			<aside class="main-sidebar sidebar-dark-primary elevation-4">
				<%@ include file="navBar.jsp" %>
			</aside>
				
		    <div class="content-wrapper">
		        <div class="main-content">
		             <div class="main-title">
						<!-- <span class="red-block"></span> -->
						<span class="title-text" id="showtitle">設定</span>
		             </div>
		
		            <!-- 搜尋bar -->
		            <form id="uploadForm" name="uploadForm">
			            <div class="search-block">
			            	<div class="search-block-line">
								<div>
			                        <label>
			                            <span class="text" >上傳角色資料(Excel)：</span>
			                            <input type="file" id="roleuploadFile" name="roleuploadFile">
			                        </label>
								</div>
								<div class="search-block-line btnventer">
						        	<div class="btn searchbtn btn-success" onclick="roleupload();">上傳</div>
					            </div>
							</div>
			            </div>

			            <div class="search-block mt-3">
			            	<div class="search-block-line">
								<div>
			                        <label>
			                            <span class="text" >上傳功能資料(Excel)：</span>
			                            <input type="file" id="functionuploadFile" name="functionuploadFile">
			                        </label>
								</div>
								<div class="search-block-line btnventer">
						        	<div class="btn searchbtn btn-success" onclick="functionupload();">上傳</div>
					            </div>
							</div>
			            </div>
			            
			            <div class="search-block mt-3">
			            	<div class="search-block-line">
								<div>
			                        <label>
			                            <span class="text" >使用者功能資料(Excel)：</span>
			                            <input type="file" id="useruploadFile" name="useruploadFile">
			                        </label>
								</div>
								<div class="search-block-line btnventer">
						        	<div class="btn searchbtn btn-success" onclick="userupload();">上傳</div>
					            </div>
							</div>
			            </div>
		            </form>
		        </div>
		    </div>
			
			<%@ include file="util.jsp" %>
			
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
			
			document.getElementById("selectSystemSetup").innerHTML = '<i class="fa-solid fa-circle nav-icon"></i>';
			document.getElementById("showtitle").innerHTML = document.getElementById("showSystemSetup").innerHTML;

			function roleupload() {

				const selectedFile = document.getElementById('roleuploadFile').files[0];
				if (selectedFile == undefined) {
					errorMsg("錯誤", "尚未選擇檔案");
					return false;
			    }
				
				let extname = selectedFile.name.substring(selectedFile.name.lastIndexOf(".")+1).toLowerCase();
				if(extname != 'xls' && extname != 'xlsx') {
					errorMsg("錯誤", "只可上傳Excel檔案");
					return false;
				}
				
				let data = new FormData(); 
				data.append("uploadFile", selectedFile)
				fetch('<%=request.getContextPath()%>/systemSetup/roleupload', {
				  method: 'POST',
				  body: data,
				})
				.then((response) => {
					return response.json();
			    })
			    .then((resp) => {
			    	let respdata = resp;
					console.log(respdata);
			    	if (respdata.code == 200) {
			    		errorMsg("", respdata.message);
			    	} else {
			    		errorMsg("錯誤", resp.message);
			    	}
			    })
				//console.log("extname");
			}

			function functionupload() {

				const selectedFile = document.getElementById('functionuploadFile').files[0];
				if (selectedFile == undefined) {
					errorMsg("錯誤", "尚未選擇檔案");
					return false;
			    }
				
				let extname = selectedFile.name.substring(selectedFile.name.lastIndexOf(".")+1).toLowerCase();
				if(extname != 'xls' && extname != 'xlsx') {
					errorMsg("錯誤", "只可上傳Excel檔案");
					return false;
				}
				
				let data = new FormData(); 
				data.append("uploadFile", selectedFile)
				fetch('<%=request.getContextPath()%>/systemSetup/functionupload', {
				  method: 'POST',
				  body: data,
				})
				.then((response) => {
					return response.json();
			    })
			    .then((resp) => {
			    	let respdata = resp;
					console.log(respdata);
			    	if (respdata.code == 200) {
			    		errorMsg("", respdata.message);
			    	} else {
			    		errorMsg("錯誤", resp.message);
			    	}
			    })
				//console.log("extname");
			}
			
			function userupload() {

				const selectedFile = document.getElementById('useruploadFile').files[0];
				if (selectedFile == undefined) {
					errorMsg("錯誤", "尚未選擇檔案");
					return false;
			    }
				
				let extname = selectedFile.name.substring(selectedFile.name.lastIndexOf(".")+1).toLowerCase();
				if(extname != 'xls' && extname != 'xlsx') {
					errorMsg("錯誤", "只可上傳Excel檔案");
					return false;
				}
				
				let data = new FormData(); 
				data.append("uploadFile", selectedFile)
				fetch('<%=request.getContextPath()%>/systemSetup/userupload', {
				  method: 'POST',
				  body: data,
				})
				.then((response) => {
					return response.json();
			    })
			    .then((resp) => {
			    	let respdata = resp;
					console.log(respdata);
			    	if (respdata.code == 200) {
			    		errorMsg("", respdata.message);
			    	} else {
			    		errorMsg("錯誤", resp.message);
			    	}
			    })
				//console.log("extname");
			}
			//錯誤訊息顯示
			function errorMsg(title, msg) {
				document.getElementById("myModalLabel").innerHTML = title;
				document.getElementById("myModalBoby").innerHTML = msg;
				document.getElementById("btnModal").click();
			}
			
		</script>
	</body>
</html>