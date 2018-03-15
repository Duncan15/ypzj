<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.List"%>
<%@ page import="com.cwc.web.ypzj.servletObj.Label" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@include file="/WEB-INF/jsp/part/head.jsp" %>
  <title>一偏之荐</title>
  <link rel="stylesheet" type="text/css" href="node_modules/jqmodal/examples/jqModal.css">
  <link rel="stylesheet" type="text/css" href="css/edit.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/part/navbar.jsp" />
  <div class="container">
    <div class="edit-panel col-xs-7">
      <div class="panel-top line fssm fsb fwb fcb">添加文章</div>
      <div class="title-line line fsm">
        <div class="tac fwn fcl">标题</div>
        <input id="title" class="input-block title-input" type="text" id="binded-title">
      </div>
      <div class="toolbar-line clearfix">
      	<div class="btn-group fr">
      		<div class="btn btn-default" id="picture-link">图片</div>
      		<div class="btn btn-default" id="link-link">链接</div>
      	</div>
        <div class="modal-group">
          <div id="picture-modal" class="jqmWindow">
            <div class="modal-title">添加图片</div>

            <form id="pic-form" class="form-horizontal" enctype="multipart/form-data" action="user/edit/img" method="post">
              <div class="form-group modal-group">
                <label class="col-xs-2 control-label">图片</label>
                <div class="col-xs-10">
                  <input id="file" required multiple="multiple" type="file" accept="image/*"  class="form-control" name="pic" value="" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <div class="btn-group btn-group-sm btn-center">
                  <button id="pic-save-btn" class="btn btn-default">保存</button>
                  <div id="pic-exit-btn" class="btn btn-default jqmClose">关闭</div>
                </div>
              </div>
            </form>
          </div>
          <div id="link-modal" class="jqmWindow">
            <div class="tac">添加链接</div>
            <form class="form-horizontal" action="" method="post">

                <div class="form-group modal-group">
                  <label class="col-xs-2 control-label">文字</label>
                  <div class="col-xs-10">
                    <input required id="describe" type="text" class="form-control" name="link-name" value="">
                  </div>
                </div>
                <div class="form-group modal-group">
                  <label class="col-xs-2 control-label">链接</label>
                  <div class="col-xs-10">
                    <input required id="link" type="url" class="form-control" name="link-url" value="">
                  </div>
                </div>
                <div class="btn-group btn-group-sm btn-center">
                  <div id="link-save-btn" class="btn btn-default">保存</div>
                  <div id="link-exit-btn" class="btn btn-default jqmClose">关闭</div>
                </div>
            </form>
          </div>
        </div>
      </div>
      <div class="content-line line fsm">
        <div class="tac fwn fcl">正文</div>
        <textarea id="editor" class="input-block content-input"></textarea>
      </div>
      <%List<Label> allLabelList=(List<Label>)session.getAttribute("labelList");%>
      <div class="label-line line fsm fwn fcl">
        <div class="tac">标签</div>
        <div class="label-input">
        </div>
        <input type="hidden" id="hd-label-input" name="labelId" value="">
        <div class="tag-panel">
          <span class="fcl fss">常用标签</span>
          <span class="fss">
            <%for(Label label:allLabelList){%>
              <a class="tag" label-id="<%=label.getId()%>"><%=label.getName()%></a>
            <%}%>

          </span>
        </div>
      </div>
      <div class="button-line line fsb fwb fcb">
        <div id="preview" class="btn btn-default fl">
          预览
        </div>
        <div id="create" class="btn btn-success fl">
          发布
        </div>
        <div id="cancel" class="btn btn-default fr">
          取消
        </div>
      </div>
    </div>

    <div class="col-xs-5">

    </div>
  </div>
  <%@include file="/WEB-INF/jsp/part/footer.jsp" %>
  <script src="https://cdn.bootcss.com/jqModal/1.4.2/jqModal.min.js"></script>
  <script type="text/javascript" src="js/uploader.js"></script>
  <script type="text/javascript" src="js/text-parser.js"></script>
  <script type="text/javascript" src="js/dealing-article.js"></script>
</body>
</html>
