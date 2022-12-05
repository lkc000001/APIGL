
function objectifyForm(formArray) {
	var returnArray = {};
	for (var i = 0; i < formArray.length; i++){
		if(formArray[i]['name'] == 'pwd') {
			var pwdVlaue = formArray[i]['value'];
			returnArray[formArray[i]['name']] = encrypt(pwdVlaue);
		} else {
			returnArray[formArray[i]['name']] = formArray[i]['value'];
		}
		
	}
	return returnArray;
}

//檢查是否為json格式
function isJson(str) {
	try {
		if (typeof str == "object") {
			return true;
		}
		
		if(typeof JSON.parse(str) == "object") {
			return true;
		}
	} catch(e) {
	}
	return false;
}

//檢查是否為陣列
function isArray(arr) {
	try {
		if (Array.isArray(arr)) {
			return true;
		}
	} catch(e) {
	}
	return false;
}

function copyEvent(id) {
	var str = document.getElementById(id);
    window.getSelection().selectAllChildren(str);
    document.execCommand("Copy")
}

function queryajax(apiurl, requ, contextPath) {
	return $.ajax({
		    type: "POST",
		    url: contextPath + apiurl,
		    dataType: "json",
		    contentType:"application/json; charset=utf-8",
		    data: JSON.stringify(requ),
		    cache:false,
		    success: function (data){
		    	document.getElementById("itemsCountDiv").style.display = 'block';
		    	document.getElementById("itemsCount").innerHTML = data.itemsCount;
		    },
		    error: function (error) {
				let title;
				var respstr = error.responseText;
				document.getElementById("itemsCount").innerHTML = 0;
				if(respstr.includes("您尚未登入")) {
					window.location.href = contextPath + "/error"
		    	} else {
		    		if(error.responseJSON.code == 404) {
		        		title = "查詢結果";
		        	} else {
		        		title = "錯誤";
		        	}
		        	errorMsg(title, error.responseJSON.message);
		    	}
		   	}
		});
}

function dofetch(httpUrl, httpMethod, httpBody) {
	fetch(httpUrl, {
		  method: httpMethod,
		  body:JSON.stringify(httpBody),
		  headers: {'Content-Type': 'application/json; charset=utf-8'}
		})
    .then((response) => {
		return response.json()
    })
    .then((resp) => {
		loaderdiv.style.display = "none";
		var functionurl = resp["functionurl"];
    	if (functionurl != null) {
			window.location.href = pagecontext + functionurl
		} else {
    		document.getElementById("error").innerHTML = resp.message;
    	}
    })
    .catch(error => {
	    document.getElementById("error").innerHTML = "系統錯誤<BR>" + error;
	});
}
		
//AES加密
function encrypt(word) {
    var key = CryptoJS.enc.Utf8.parse(keystr);
	var iv = CryptoJS.enc.Utf8.parse(ivstr);
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {iv:iv,mode:CryptoJS.mode.CFB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

//AES解密
function decrypt(word) {
    var key = CryptoJS.enc.Utf8.parse(keystr);
	var iv = CryptoJS.enc.Utf8.parse(ivstr);
    var decrypt = CryptoJS.AES.decrypt(word, key, {iv:iv,mode:CryptoJS.mode.CFB,padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}

$('#queryDate').datetimepicker({
	timepicker:false,
	format:'Y/m/d',
	mask:true,
});
