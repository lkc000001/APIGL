<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="addbtn-block plural controlhidden mt-2" id="itemsCountDiv">
	<div class="showleft">
		查詢結果：共 <span style="color: red;" id="itemsCount"></span> 筆
	</div>
             
	<div class="showright">
		顯示筆數： 
		<select id="showcount" name="showcount" onchange="doquery();">
			<option value="10" selected>10</option>
			<option value="25">25</option>
			<option value="50">50</option>
			<option value="100">100</option>
		</select>
	</div>
</div>