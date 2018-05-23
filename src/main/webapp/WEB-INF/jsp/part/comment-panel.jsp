<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" href="css/part/comment-panel.css">
<style>
.content-input {
  height: 100px;
}
#reply-content {
  width: 93%;
  margin: 0 20px;
  border-radius: 5px;
}
</style>
<%
  String hostIdStr=request.getParameter("hostId");
  String messageTypeStr=request.getParameter("messageType");
%>
<div class="comment-line line fcb">
  <div class="param-area hidden">
    <div id="tmpTopCommentId"></div>
    <div id="tmpReceiverId"></div>

    <div class="hidden" id="hostId">
      <%=hostIdStr%>
    </div>
    <div class="hidden" id="messageType">
      <%=messageTypeStr%>
    </div>
  </div>
  <div class="comment-top line fwb fsb">评论</div>
  <div class="comment-list fwn fsm" id="comment-list">

  </div>
  <div class="modal-group">
    <div id="reply-modal" class="jqmWindow">
      <div class="modal-title">编辑回复</div>
      <form id="reply-form" class="form-horizontal">
        <div class="form-group modal-group">
          <textarea id="reply-content" class="content-input"></textarea>
        </div>
        <div class="form-group">
          <div class="btn-group btn-group-sm btn-center">
            <button id="reply-save-btn" class="btn btn-default">评论</button>
            <div id="reply-exit-btn" class="btn btn-default jqmClose">关闭</div>
          </div>
        </div>
      </form>
    </div>
  </div>
  <div class="comment-editbar">
    <div class="editbar-top line fwb fsb">你的评论</div>
    <textarea id="comment-content" class="input-block content-input"></textarea>
    <div class="button-line line tac">
      <div class="btn btn-success" id="comment-btn">评论</div>
    </div>
  </div>
</div>
