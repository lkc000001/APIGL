<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-Hant-TW">
	<head>
    	<meta name="viewport" content="width=device-width" />
	    <title>台灣企銀-日誌查詢系統ApiGuiLog</title>
	
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
		                <span class="title-text" id="showtitle">ApiGuiLog查詢</span>
		            </div>
		
		            <!-- 搜尋bar -->
		            <form id="myForm" name="myForm">
		            <div class="search-block">
		                <div class="search-block-line">
		                    <div>
		                        <label>
		                            <span class="text" >搜尋日期：</span>
		                            <input class="queryDate-width" type="text" id="queryDate" name="queryDate">
		                        </label>
		                        &nbsp;&nbsp;&nbsp;
		                        <label>
		                            <span class="text">起迄時間：</span>
		                            <input type="time" id="timeStart" name="timeStart">~ <input type="time" id="timeEnd" name="timeEnd">
		                        </label>
		                    </div>
		                </div>
		                <div class="search-block-line">
		                    <div>
		                        <label class="controlhidden">
		                            <span class="text">身分證字號或統編：</span>
		                            <input type="text" id="queryUserId" name="queryUserId">
		                        </label>
		                        
		                        <label>
		                            <span class="text">關鍵字：</span>
		                            <input type="text" id="querydata" name="queryData">
		                        </label>
		                    </div>
		                </div>
		                <div class="search-block-line">
		                    <div>
		                        <label>
		                            <span class="text">發送模式：</span>
		                            <select id="queryType" name="queryType">
									    <option value="" selected></option>
									    <option value="1">1(request)</option>
									    <option value="2">2(response)</option>
									</select>
		                        </label>
		                        &nbsp;&nbsp;&nbsp;
		                        <label>
		                            <span class="text">URL：</span>
		                            <input type="text" id="queryUrl" name="queryUrl">
		                        </label>
		                    </div>
		                </div>
		                <div class="search-block-line btnventer">
		                    <div class="btn searchbtn newbtn-gray" onclick="doClear(0);">清除</div>
		                    &nbsp;&nbsp;
		                    <div class="btn searchbtn newbtn-yellow" onclick="doquery();">搜尋</div>
		                </div>
		            </div>
		            </form>
		            
		           	<%@ include file="jsgridtop.jsp" %>
		            <div id="jsGrid"></div>
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
			var showcount = document.getElementById("showcount").value;
			document.getElementById("queryTable").value = 'ApiGuiLog';
			document.getElementById("selectApiGuiLog").innerHTML = '<i class="fa-solid fa-circle nav-icon"></i>';
			document.getElementById("showtitle").innerHTML = document.getElementById("showApiGuiLog").innerHTML;
			
			//查詢資料
			function doquery() {
				let showcount = document.getElementById("showcount").value;
				clicknum = 0;
				$("#jsGrid").jsGrid("reset");
				dojsGrid('queryApiGuiLog', showcount);
			}
		
			//顯示查詢結果
			function dojsGrid(apiurl, showcount) {
				
				const sendstatus = [
				    { Name: "request", Id: "1" },
				    { Name: "response", Id: "2" }
				];
				
				const errortype = [
					{ Name: "ERROR", Id: "1" },
				    { Name: "WARN", Id: "2" },
				    { Name: "INFO", Id: "3" },
				    { Name: "DEBUG", Id: "4" }
				];
				
				let message = "無資料";//"無資料"
				
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
							        return queryajax("/apiGuiLog/" + apiurl, requ, "<%=request.getContextPath()%>");
						    	}
						    }
						},
						fields: [
							{ name: "apiGuiId", type: "text", width: 40 , title:"索引" },
							{ name: "apiGuiUserId", type: "text", width: 100, title:"員工編號" },
							{ name: "apiGuiSendStatus", type: "select", items: sendstatus, valueField: "Id", textField: "Name", width: 60, title:"發送模式"},
							{ name: "apiGuiUrl", type: "text", width: 180, title:"URL"},
							//{ name: "apiGuiLogType", type: "select", items: errortype, valueField: "Id", textField: "Name", width: 60, title:"type"},
							{ name: "showDate", type: "text", title: "訊息日期"},
							{ name: "showTime", type: "text", title: "訊息時間"},
							{ 
								itemTemplate: function(_, item) {
					                return $("<button>").text("詳細")
					                	.on("click", function() {
					        				document.getElementById("queryId").value = item.apiGuiId;
					                		document.showDetailed.submit();
					                	});
					          	}, sorting: false
							}
						]
				})
			}
		
			//清除查詢資料
			function doClear(num) {
				checkClear = 1;
				$("#jsGrid").jsGrid("reset");
				
				if(num == 0) {
					document.getElementById("itemsCountDiv").style.display = 'none';
					document.getElementById("queryUserId").value = null;
					document.getElementById("querydata").value = null;
					document.getElementById("queryDate").value = null;
					document.getElementById("queryType").value = null;
					document.getElementById("queryUrl").value = null;
					document.getElementById("timeStart").value = null;
					document.getElementById("timeEnd").value = null;
				}
				$("#jsGrid").children().remove();
				checkClear = 0;
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
			
		</script>
	</body>
</html>