<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="zh-Hant-TW">
	<head>
	    <meta name="viewport" content="width=device-width" />
	    <title>台灣企銀-日誌查詢系統詳細頁面</title>
	
	    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui.theme-1.12.1.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/jQuery/DateTimePicker/jquery.datetimepicker-2.5.20.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/Font-Awesome/css/all.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/overlayScrollbars/OverlayScrollbars.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/AdminLTE/adminlte-3.1.0.min.css" rel="stylesheet" />
	    <link href="<%=request.getContextPath()%>/component/jsgrid/jsgrid.min.css" rel="stylesheet" type="text/css"/>
	    <link href="<%=request.getContextPath()%>/component/jsgrid/jsgrid-theme.min.css" rel="stylesheet" type="text/css"/>
	    <link href="<%=request.getContextPath()%>/component/CSS/style.css" rel="stylesheet"/>
	
	</head>
	<body class="sidebar-mini layout-fixed layout-navbar-fixed layout-footer-fixed sidebar-collapseX" style="height: auto;">
		<div class="wrapper">
		    <nav class="main-header navbar navbar-expand navbar-white navbar-light">
				<%@ include file="header.jsp" %>
			</nav>
			
			<aside class="main-sidebar sidebar-dark-primary elevation-4">
				<%@ include file="navBar.jsp" %>
			</aside>
		
		    <div class="content-wrapper">
				<div class="main-content">
					<div class="main-title">
						<span class="title-text">詳細頁面</span>
					</div>
			
					<div id="bobyhtml"></div>
					
					<div class="pagebtn">
						<div class="btn  newbtn-gray" onclick="doreturn();">返回</div>
					</div>
			
				</div>
			</div>
			
			<!--（Modal） -->
			<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal" id="btnModal" style="display: none;"></button>
		
			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-scrollable">
					<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title" id="myModalLabel"></h4>
						</div>
						
						<div class="wordWrap modal-body" id="myModalBoby"></div>
						
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" onclick="copyEvent('myModalBoby')" >複製</button>
							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">關閉</button>
						</div>
					</div>
				</div>
			</div>
			
			
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
		
		<script language="JavaScript" type="text/JavaScript">
		
			var data = '<%=request.getAttribute("detailedresp") %>';
			var queryTable = '<%=request.getAttribute("queryTable") %>';
			//console.log(queryTable);
			selectlist(queryTable)
			
			window.onload = function(){
				document.getElementById("bobyhtml").innerHTML = creatTable(data,0);
			}
			//產生顯示的table
			function creatTable(dataJson, index) {
				let myObj = JSON.parse(dataJson);
		
				let text = "";
				if(index == 0){
					text = "<table class='sdtable'>"
					text += "<tr><th width=20%> KEY </th width=80%> <th> VALUE </th></tr>";
				} else {
					text = "<table class='subtable'>"
				}
				for (key in myObj) {
					if(key != 'pageIndex' && key != 'pageSize' && key != 'sortField' && key != 'sortOrder' && key != 'showDate' && key != 'showTime') {
						//檢查是否為JSON
						if(isJson(myObj[key])) {
							//JSON中的JSON或陣列需另外處理
							if (typeof myObj[key] == "object") {
								//檢查是否為陣列
								if(isArray(myObj[key])) {
									let arrayObj = myObj[key];
									text += "<tr><td class='key' width=20%><font size='4'>" + key +  "</font></td><td width=80%>";
									if(arrayObj.length > 0) {
										text += "<table class='subtable'><tr><td width=20%></td>";
											//設定陣列title
											for(titlevaluekey in arrayObj[0]) {
												text += "<td width=40%><font size='4'>" + titlevaluekey +  "</font></td>";
											}
											//設定陣列value
											for(arraykey in arrayObj) {
												text += "<tr><td width=20%><font size='4'>" + arraykey +  "</font></td>";
												for(valuekey in arrayObj[arraykey]) {
													text += "<td class='table-ellipsis' width=40%><font size='4'>" + arrayObj[arraykey][valuekey] + "</font></td>";
												} 
												text += "</tr>";
											}
									
										text += "</td></tr></table>";
									}
									text += "</td>";
								} else {
									text += "<tr><td class='subtable-key' width=30%><font size='4'>" + key +  "</font></td><td class='table-ellipsis' width=70%>" + creatTable(JSON.stringify(myObj[key]),1) + "</td></tr>";
								}
							} else {
								text += "<tr><td class='subtable-key' width=30%><font size='4'>" + key +  "</font></td><td class='table-ellipsis' width=70%>" + creatTable(myObj[key],1) + "</td></tr>";
							}
						} else {
							text += "<tr><td class='subtable-key' width=20%><font size='4'>" + key +  "</font></td><td class='table-ellipsis' width=80% onclick=doshow(this.innerText)>" + myObj[key] + "</td></tr>";
						}
					}
				}
				text += "</table>"
				return text;
			}
			
			//回上一頁
			function doreturn() {
				history.back();
			}
			
			//顯示過長的json內容
			function doshow(element) {
				if(element.length > 45) {
					document.getElementById("myModalLabel").innerHTML = "詳細資訊";
					document.getElementById("myModalBoby").innerHTML = element;
					document.getElementById("btnModal").click();
				}
			}
			
			function selectlist(table) {
				let showstr = '<i class="fa-solid fa-circle nav-icon"></i>';
				switch(table) {
				case "LpmApiLog":
					document.getElementById("selectLpmApiLog").innerHTML = showstr;
					break;
				case "TwPayLineBindLog":
					document.getElementById("selectTwPayLineBindLog").innerHTML = showstr;
					break;
				case "RewardApiLog":
					document.getElementById("selectRewardApiLog").innerHTML = showstr;
					break;
				case "IcpApiLog":
					document.getElementById("selectIcpApiLog").innerHTML = showstr;
					break;
				case "MtpApiLog":
					document.getElementById("selectMtpApiLog").innerHTML = showstr;
					break;
				case "Hpg5000ApiLog":
					document.getElementById("selectHpg5000ApiLog").innerHTML = showstr;
					break;
				case "SmsOtpLog":
					document.getElementById("selectSmsOtpLog").innerHTML = showstr;
					break;
				case "ApiGuiLog":
					document.getElementById("selectApiGuiLog").innerHTML = showstr;
					break;
				case "UserTrackLog":
					document.getElementById("selectUserTrackLog").innerHTML = showstr;
					break;	
				}
			}
		</script>
	</body>
</html>