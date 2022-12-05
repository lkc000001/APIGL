<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<form class="controlhidden" id="showDetailed" method="POST" name="showDetailed" action="<%=request.getContextPath()%>/showDetailed/">
	<input type="text" id="queryId" name="queryId">
	<input type="text" id="queryTable" name="queryTable">
</form>

<!--（Modal） -->
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal" id="btnModal" style="display: none;"></button>

<!-- Modal -->
<div class="modal" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="wordWrap modal-body">
				<h4 id="myModalBoby"></h4>
			</div>
			<div class="modal-footer">
				<button type="button" id="copybtn" class="btn btn-primary controlhidden" onclick="copyEvent('myModalBoby')" >複製</button>
				&nbsp;&nbsp;
				<button type="button" class="btn newbtn-gray" data-bs-dismiss="modal">關閉</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="deleteModalTitle"></h4>
			</div>
			<div class="wordWrap modal-body">
				<h4 id="deleteModalBoby"></h4>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn newbtn-gray" data-bs-dismiss="modal">關閉</button>
				&nbsp;&nbsp;
				<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="dodelete();">確認</button>
			</div>
		</div>
	</div>
</div>