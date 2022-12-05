<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.removeAttribute("loginMessage");
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui.theme-1.12.1.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/component/Font-Awesome/css/all.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.min.css" rel="stylesheet" />
    <link href="<%=request.getContextPath()%>/component/loaders/loaders.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/component/CSS/normalize.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/component/CSS/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/component/CSS/login.css">
    
    <title>台灣企銀-交易查詢系統-登入畫面</title>
</head>
<body>
	<div class="whitebg">
        <div class="logo">
            <img src="<%=request.getContextPath()%>/component/Images/logo.svg" alt="台灣企銀">
        </div>
        <div class="s-title">
            <span class="s-txt">登入系統</span>
        </div>
        <form id="loginForm" name="loginForm">
	        <div id="inputdiv">
	            <div class="inputblock">
	                <label for="name">使用者名稱：</label>
	                <input type="text" id="account" name="account">
	            </div>
	    
	            <div class="inputblock">
	                <label for="pwd">使用者密碼：</label>
	                <input type="password" id="pwd" name="pwd" maxlength="20">
	                <input type="hidden" id="shapwd" name="shapwd">
	            </div>

	                
	            <div class="inputblock" id="authCode">
	                <label for="code">輸入驗證碼：</label>
	                <input type="text" id="usercode" name="usercode" class="inputcode" maxlength="4">
	                <div class="codeimg">
	                	<img id="authCodeImage" />
	                </div>
	                <i class="fa-solid fa-rotate" onclick="doauthCode();"></i>
	            </div>
    		</div>

			<div class="container">
			      <div class="loader-inner ball-spin-fade-loader" id="loaderdiv"></div>
			</div>
			<div class="error" id="error">
            	<br>
            	<br>
            </div>
			
            <div id="btndiv" class="loginbtn">
                <a  class="btn lg newbtn-gray" onclick="doClear();">清除</a>
				&nbsp;
                <a class="btn lg newbtn-yellow" onclick="doLogin();">登入</a>
            </div>
            <div id="returnEportal" class="eportalbtn">
            	<a class="btn lg newbtn-yellow" onclick="doreturn();">返回Eportal</a>
            </div>
        </form>
    </div>

	<form method="GET" id="loginsuccess" name="loginsuccess" action="<%=request.getContextPath()%>/lpmApiLog/">
	</form>
 	
 	<script src="<%=request.getContextPath()%>/component/jQuery/jquery-3.6.0.min.js"></script>
    <script src="<%=request.getContextPath()%>/component/jQuery-UI/jquery-ui-1.12.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/component/jQuery/DateTimePicker/jquery.datetimepicker-2.5.20.min.js"></script>
    <script src="<%=request.getContextPath()%>/component/Bootstrap/bootstrap.bundle.min.js"></script>
    <script src="<%=request.getContextPath()%>/component/JS/apiGLUtil.js"></script>
    <script src="<%=request.getContextPath()%>/component/aes/aes.js"></script> 
    <script src="<%=request.getContextPath()%>/component/cdnjs/sjcl.js"></script> 
    <script src="<%=request.getContextPath()%>/component/loaders/loaders.css.js"></script> 
    
    <script>
    	$('.loader-inner').loaders();
		var clicknum = 0;
		var authErrorNum = 0;
		var keystr;
		var ivstr;
		var pagecontext = '<%=request.getContextPath()%>';
		doClear()
		//產生驗證碼
		doauthCode(); 
		//取得AES KEY
		dogetKey();

		//ePortal登入
		var ePortalAuthCode = '<%=request.getAttribute("ePortalAuthCode") %>';
		if(ePortalAuthCode != 'null') {
			btndiv.style.display = "none";
			inputdiv.style.display = "none";
			loaderdiv.style.display = "block";
			returnEportal.style.display = "flex";
			document.getElementById("error").innerHTML = "ePortal登入中 <br> &nbsp;";
			doEPortalLogin();
		}
		
		async function doEPortalLogin() {
			let requ = {"account": "", "authcode": ePortalAuthCode};
			await dofetch(pagecontext + '/login/ePortalLogin', 'POST', requ);
		}
		
		function doLogin() {
			let pwdstr = document.getElementById("pwd").value + keystr;
			var hash = sjcl.hash.sha256.hash(pwdstr);
			document.getElementById("shapwd").value = sjcl.codec.hex.fromBits(hash);
			if (checkData()) {
				let url = pagecontext + '/login/login';
				let requ = objectifyForm($("#loginForm").serializeArray());
				dofetch(url, 'POST', requ);
			 }
		}
		
		function doauthCode() {
			document.getElementById("authCodeImage").src = "<%=request.getContextPath()%>/login/createAuthCodeImage?" +Math.random();
		}
		
		//清除查詢資料
		function doClear() {
			document.getElementById("account").value = null;
			document.getElementById("pwd").value = null;
			document.getElementById("usercode").value = null;
		}
		
		//檢查欄位是否為空白
		function checkData() {
			if (document.getElementById("account").value == '') {
				document.getElementById("error").innerHTML = "帳號不能為空白 <br> &nbsp;";
				return false;
			};
			
			if (document.getElementById("pwd").value == '') {
				document.getElementById("error").innerHTML = "密碼不能為空白 <br> &nbsp;";
				return false;
			};
			
			if (document.getElementById("usercode").value == '') {
				document.getElementById("error").innerHTML = "驗證碼不能為空白 <br> &nbsp;";
				return false;
			};
			
			if (document.getElementById("usercode").value.length < 4) {
				document.getElementById("error").innerHTML = "驗證碼輸入錯誤 <br> &nbsp;";
				return false;
			};
			return true;
		}
		
		function dogetKey() {
			fetch('<%=request.getContextPath()%>/login/getKey')
			    .then((response) => {
			    	if (response.ok) {
			    		return response.json();
			    	} else {
			    		document.getElementById("error").innerHTML = "取得KEY失敗<BR> ";
			    	}
			    })
			    .then( (response) => {
			    	keystr = response['keystr'];
					ivstr = response['ivstr'];
			    })
			    .catch((error) => {
			    	document.getElementById("error").innerHTML = "系統錯誤<BR>" + error;
			 });
		}

		function doreturn() {
			window.location.href = "${eportalHomepage}"
		}
	</script>
</body>
</html>