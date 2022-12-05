<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.formosoft.sso.tbb.*"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.util.Iterator"%>
<%
	request.setCharacterEncoding("UTF-8");

	out.println("---TBB PortalAPI Java版測試---<br>");

	String ePortal_URL = "http://10.16.22.109/TBBEPortalServlet/eportal"; // SSO ePortal URL，實際設定值請向行員端ePortal管理者申請
	// SSO API帳號與密碼 於 [ePortal -> ＡＰ管理 -> API User] 中設定)
	// SSO 應用系統名稱 於[ePortal -> ＡＰ管理 -> 新增/查詢網路服務] 設定)
	String sAPIUser = "APIGL"; // SSO API帳號，實際設定值請向行員端ePortal管理者申請
	String sAPIPwd = "!QAZ2wsx"; // SSO API密碼，實際設定值請向行員端ePortal管理者申請
	String sAPName = "APIGL"; // SSO 應用系統名稱(網路服務英文名稱)，實際設定值請向行員端ePortal管理者申請

	out.println("SSO ePortal URL = " + ePortal_URL + "<br>");
	out.println("SSO API帳號(API User) = " + sAPIUser + "<br>");
	out.println("SSO API密碼(Secret) = " + sAPIPwd + "<br>");
	out.println("SSO 應用系統名稱(網路服務英文名稱) = " + sAPName + "<br>");

	out.println("------------------------<br>");

	String sBranch = ""; // 行員分行/部門代碼(DEPT_CODE)
	String sGroupName = ""; // 群組名稱(GROUP_NAME)
	int iAccountID = 0; // 帳號序號(ACCOUNT_ID)
	String sAccount = ""; // 帳號名稱(ACCOUNT)
	String sName = ""; // 姓名(NAME)

	// 檢查行員使用者是否登入並取得使用者物件
	String sUserIP = request.getRemoteAddr();
	out.println("行員使用者IP = " + sUserIP + "<br>"); // 範例未實作X-Forwarded-For

	out.println("------------01_init：AP初始化------------<br>");

	SSOMgr ssomgr = new SSOMgr(ePortal_URL);
	Principal prn = null;
	int iRtn = ssomgr.init(sAPIUser, sAPIPwd, sAPName);
	String sAuthCode = request.getParameter("authCode");
	
	out.println("sAuthCode = " + sAuthCode + "<br>");
	if (iRtn == 0) {
		out.println("01_init：AP初始化成功<br>");

		out.println("------------02_checklogin：User AuthCode驗證------------<br>");
		if (ssomgr.checkLogon(sAuthCode, sUserIP)) {
			out.println("02_checklogin：User AuthCode驗證成功<br>");

			prn = ssomgr.getPrincipal();
			if (prn == null) {
				out.println(ssomgr.getLastError() + ",getPrincipal failed<br>");
				return;
			}

			// 將Portal API物件存於session中，
			// 往後如有需要使用，直接由session中取用，不需再做checkLogon。
			// 此步驟務必實作，才能持續原來Portal的session。
			session.setAttribute("PortalObject", ssomgr);
			out.println("行員使用者資訊：<br>");

			Dept dept = prn.getDept();
			sBranch = dept.getCode(); // 取得行員分行/部門代碼

			out.println("分行/部門代碼(Branch)：" + sBranch + "<br>");

			// 取得行員所屬的群組
			GroupCollection gCol = prn.getGroups();
			if (gCol != null) {
				Iterator it = null;
				it = gCol.iterator();
				Group grp = null;
				while (it.hasNext()) {
					grp = (Group) it.next();
					// 行員群組名稱(GroupName)
					sGroupName = grp.getName();
					out.println("群組名稱(GroupName)：" + sGroupName + "<br>");
				}
			}

			iAccountID = prn.getId();
			sAccount = prn.getAccount();
			sName = prn.getName();

			out.println("帳號序號(AccountID)：" + iAccountID + "<br>");
			out.println("帳號名稱(Account)：" + sAccount + "<br>");
			out.println("姓名(Name)：" + sName + "<br>");

			// 如果想讓使用者用本系統後，還可以回到SSO系統，就不要call free，讓SSO系統的session可以一直keep
			// ssomgr.free(); 
		} else {
			out.println("02_checklogin：User AuthCode驗證失敗<br>");
			out.println("User authorization fail，SSO Session過期，請重新登入SSO。<br>");
			out.println("錯誤代碼：" + ssomgr.getLastError() + ",User authorization fail!<br>");
			return;
		}
	} else {
		out.println("01_init：AP初始化失敗<br>");
		out.println("Init SSOMgr fail，SSO初始化失敗，請確定以下設定：<br>");
		out.println("1. <SSO設定>內之設定值是否正確。<br>");
		out.println("2. AP Server與ePortal連線之防火牆是否已開啟。<br>");
		out.println(iRtn + ",Init SSOMgr fail!<br>");
		return;
	}
%>