<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
	String loginMessage = session.getAttribute("loginMessage") == null ? null : (String)session.getAttribute("loginMessage");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta name="viewport" content="width=device-width" />
	    <title>台灣企銀-日誌查詢系統ERROR</title>
	
	    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui.theme-1.12.1.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/Font-Awesome/css/all.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/overlayScrollbars/OverlayScrollbars.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/AdminLTE/adminlte-3.1.0.min.css" rel="stylesheet" />
	    <link rel="stylesheet" href="<%=request.getContextPath()%>/component/CSS/style.css">
	
	</head>
	<body class="sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed sidebar-collapseX" style="height: auto;">
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
						<!-- <span class="red-block"></span> -->
						<span class="title-text">ERROR</span>
		             </div>
					<div class="errorMsg" id="errorMsg"> </div>
					
					<div class="pagebtn">
						<div id = "returnbtn" class="btn newbtn-gray" onclick="doreturn();">返回</div>
					</div>
			
				</div>
			</div>
		    <!-- 頁尾 -->
	        <footer class="main-footer">
	           <%@ include file="footer.jsp" %>
	        </footer>
	    </div>
	    
	    <script src="<%=request.getContextPath()%>/component/jQuery/jquery-3.6.0.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.bundle.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/overlayScrollbars/jquery.overlayScrollbars.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/AdminLTE/adminlte-3.1.0.min.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/component/jsgrid/jsgrid.min.js"></script>
	    <script src="<%=request.getContextPath()%>/component/JS/all.js"></script>
	    
		<script language="JavaScript" type="text/JavaScript">
			var errorMsg = '<%=request.getAttribute("errorMsg") %>';
			var loginMessage = '<%=loginMessage%>';
			
			const xhr = new XMLHttpRequest();
			xhr.open('GET', '<%=request.getContextPath()%>/eportalHomepage', false);
			xhr.send(null);
			var eportalHomepage = xhr.responseText;
					
			window.onload = function(){
				if(errorMsg == 'null') {
					errorMsg = "系統錯誤";
				}
				if(loginMessage != 'null') {
					document.getElementById("returnbtn").innerHTML = '確認';
					document.getElementById("errorMsg").innerHTML = loginMessage;
					window.setTimeout("window.location.href = '" + eportalHomepage + "'" , 3000);
				} else {
					document.getElementById("errorMsg").innerHTML = errorMsg;
				}
			}
			
			//回上一頁
			function doreturn() {
				if(loginMessage != 'null') {
					window.location.href =  eportalHomepage;
				} else {
					<% session.setAttribute("querysession", "return"); %>;
					history.back();
				}
			}
			
		</script>
	</body>
</html>