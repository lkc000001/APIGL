<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ApiGL.entity.User" %>   
<%  User user = session.getAttribute("user") == null ? null : (User)session.getAttribute("user"); 
	Integer userid = 0;
	String username = "";
	String name = "";
	String branch = "";
	String groupName = "";
	
	if (user != null) {
		userid = user.getAccountId();
		username = user.getAccount();
		name = user.getName();
		branch = user.getBranch();
		groupName = user.getGroupName();
	}
%>
<!-- 靠左功能區 -->
<ul class="navbar-nav">
    <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" data-enable-remember="true" href="#" role="button"><i class="fas fa-bars"></i></a>
    </li>
    <li class="nav-item d-none d-sm-inline-block">
        <a href="" target="_self" class="nav-link nav-title">日誌查詢系統</a>
    </li>
</ul>

<!-- 靠右功能區 -->
<div class="navbar-nav ml-auto">
    <div class="member-block">
        <div class="member">
            <!-- <div class="num">員工編號：000000</div> -->
            <div class="name">姓名：<span><%=name %></span></div>
            <div class="membericon"><i class="fa-solid fa-circle-chevron-down"></i></div>
        </div>       
        <div class="Competence" id="Competence">
            <div>
                <div class="Competence-line">分行/部門代碼：<%=branch %></div>
                <div class="Competence-line">群組名稱：<%=groupName %></div>
                <div class="Competence-line">帳號序號：<%=userid %></div>
                <div class="Competence-line">帳號名稱：<%=username %></div>

                <a href="<%=request.getContextPath()%>/logout/" class=" btn newbtn-gary Competence-btn">登出</a>
            </div>
        </div>
    </div>
</div>