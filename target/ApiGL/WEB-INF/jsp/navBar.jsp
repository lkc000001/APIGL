<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ApiGL.entity.ApiGLFunction" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%
	Object navbarFunction = session.getAttribute("navbarFunction"); 
%>
<!-- 企業標誌 Logo -->
<div href="#" class="brand-link elevation-2 navbarLogo">
    <img src="<%=request.getContextPath()%>/component/Images/header-logo.svg" alt="台新銀行XTS管理系統" class=" elevation-3 logoimg"/>
</div>

<!-- 選單區 -->
<div class="main-sidebar sidebar os-host os-theme-light os-host-overflow os-host-overflow-y os-host-resize-disabled os-host-transition os-host-scrollbar-horizontal-hidden">
    <div class="os-resize-observer-host observed">
        <div class="os-resize-observer" style="left: 0px; right: auto;"></div>
    </div>
    <div class="os-size-auto-observer observed" style="height: calc(100% + 1px); float: left;">
        <div class="os-resize-observer"></div>
    </div>
    <div class="os-content-glue" style="margin: 0px -8px; width:30px;"></div>

    <!-- 選單資訊 -->
    <nav class="mt-2 nav-collapse-hide-child">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false" id="listHtml">
            <!--  
            <li class="nav-item menu-is-opening menu-open" id="list1Html"></li>
            <li class="nav-item menu-is-opening menu-open" id="list2Html"></li>
            <li class="nav-item menu-is-opening menu-open" id="list3Html"></li>-->
        </ul>
    </nav>

    <!-- 選單的捲軸列 -->
    <div class="os-scrollbar os-scrollbar-horizontal os-scrollbar-unusable os-scrollbar-auto-hidden">
        <div class="os-scrollbar-track">
            <div class="os-scrollbar-handle" style="width: 100%; transform: translate(0px);"></div>
        </div>
    </div>
    <div class="os-scrollbar os-scrollbar-vertical os-scrollbar-auto-hidden">
        <div class="os-scrollbar-track">
            <div class="os-scrollbar-handle" style="height: 47.9359%; transform: translate(0px);"></div>
        </div>
    </div>
    <div class="os-scrollbar-corner"></div>
</div>

<script>
   	var navbarFunction = '<%=navbarFunction%>';
   	if(navbarFunction != 'null') {
   		document.getElementById("listHtml").innerHTML = navbarFunction;
   	}
   
</script>