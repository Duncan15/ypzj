<%@ page import="com.cwc.web.ypzj.model.obj.Brocast" %>
<%@ page import="com.cwc.web.ypzj.model.DAO.BrocastRepository" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="node_modules/jqmodal/examples/jqModal.css">
    <link rel="stylesheet" type="text/css" href="css/define.css">
    <link rel="icon" type="image/x-icon" href="img/favicon.ico">
	<title>一偏之荐</title>
	<style>
		body {
			position: relative;
		}
		ul.nav-pills {
			top: 100px;
			position: fixed;
		}
    .content-item {
            padding-top: 50px;
            padding-left: 20px;
            padding-right: 20px;
    }
    .sidebar-item {
            padding: 20px 20px;
    }
    li.active a{
      font-weight:bold;
    	background: rgba(0,0,0,0.04)!important;
      color:#333!important;
    }
    li a {
      color: #333;
    }
    .blue-line {
      margin: 10px 0;
      padding: 10px 10px;
      border: 1px solid #0895f1;
      border-radius: 5px;
    }
    .content-input {
      height: 100px;
    }
    #brocast-content {
      width: 93%;
      margin: 0 20px;
      border-radius: 5px;
    }
	</style>
</head>
<body data-spy="scroll" data-target="#myScrollspy" data-offset="80">
<jsp:include page="/WEB-INF/jsp/part/navbar.jsp"/>
<div class="container">
	<div class="row">
		<div class="col-sm-3 nav" id="myScrollspy">
			<ul class="nav nav-pills nav-stacked">
				<li class="sidebar-item fwl fcb fsb active"><a href="#carousel">首页轮播</a></li>
				<li class="sidebar-item fwl fcb fsb"><a href="#brocast">公告管理</a></li>
				<li class="sidebar-item fwl fcb fsb"><a href="#frendlink">友链管理</a></li>
				<li class="sidebar-item fwl fcb fsb"><a href="#article-manege">文章管理</a></li>
                <li class="sidebar-item fwl fcb fsb"><a href="">站内信</a></li>
			</ul>
		</div>
    <div class="col-xs-9">
      <div class="modal-group">
        <div id="picture-modal" class="jqmWindow">
          <div class="modal-title">添加图片</div>
          <form id="pic-form" class="form-horizontal"  enctype="multipart/form-data" method="post">
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
        <div id="brocast-modal" class="jqmWindow">
          <div class="modal-title">编辑公告</div>
          <form id="brocast-form" class="form-horizontal">
            <div class="form-group modal-group">
              <textarea id="brocast-content" class="content-input"></textarea>
            </div>
            <div class="form-group">
              <div class="btn-group btn-group-sm btn-center">
                <button id="brocast-save-btn" class="btn btn-default">保存</button>
                <div id="brocast-exit-btn" class="btn btn-default jqmClose">关闭</div>
              </div>
            </div>
          </form>
        </div>
        <div id="link-modal" class="jqmWindow">
          <div class="modal-title">添加友链</div>
          <form class="form-horizontal">
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
        <div class="list-group" >
            <div class="content-item list-group-item" id="carousel" >
              <div class="carousel-editor clearfix">
              	<div class="btn-group fr">
              		<div class="btn btn-default" id="picture-link">添加图片</div>
              	</div>
              </div>
              <div class="carousel-line blue-line">
                <jsp:include page="/WEB-INF/jsp/part/pic-line.jsp">
                    <jsp:param name="mode" value="carousel" />
                    <jsp:param name="itemNum" value="4" />
                </jsp:include>
              </div>
            </div>
            <div class="content-item list-group-item" id="brocast">
                <div class="brocast-editor clearfix">
                  <div class="btn-group fr">
                		<div class="btn btn-default" id="brocast-link">编辑公告</div>
                	</div>
                </div>
                <div class="brocast-line blue-line">
                    <%Brocast brocast= BrocastRepository.getNewestBrocast();
                      if(brocast!=null){
                    %>
                        <%=brocast.getContent()%>
                    <%}%>
                </div>
            </div>
            <div class="content-item list-group-item" id="frendlink">
              <div class="frendlink-editor clearfix">
                <div class="btn-group fr">
                  <div class="btn btn-default" id="link-link">添加友链</div>
                </div>
              </div>
              <div class="frendlink-line" style="margin: 10px 0;">
                <jsp:include page="/WEB-INF/jsp/part/link-list.jsp">
                    <jsp:param name="itemNum" value="4" />
                </jsp:include>
              </div>
            </div>
            <div class="content-item list-group-item" id="article-manege">
              <div id="content-manage" class="manage-board blue-line">
                <div class="modal-title">内容管理</div>
                <form class="form-horizontal">
                    <div class="form-group modal-group">
                      <label class="col-xs-2 control-label">标题</label>
                      <div class="col-xs-10">
                        <input required type="text" class="form-control" name="article-name" value="">
                      </div>
                    </div>
                    <div class="form-group modal-group">
                      <label class="col-xs-2 control-label">作者</label>
                      <div class="col-xs-10">
                        <input required type="url" class="form-control" name="author-name" value="">
                      </div>
                    </div>
                    <div class="btn-group btn-group-sm btn-center">
                      <div id="ban-article-btn" class="btn btn-default">封禁文章</div>
                      <div id="unban-article-btn" class="btn btn-default jqmClose">解禁文章</div>
                    </div>
                </form>
              </div>
              <div id="account-manage" class="manage-board blue-line">
                <div class="modal-title">账号管理</div>
                <form class="form-horizontal">
                    <div class="form-group modal-group">
                      <label class="col-xs-2 control-label">账号</label>
                      <div class="col-xs-10">
                        <input required type="text" class="form-control" name="account-name" value="">
                      </div>
                    </div>
                    <div class="btn-group btn-group-sm btn-center">
                      <div id="ban-account-btn" class="btn btn-default">封禁账号</div>
                      <div id="unban-account-btn" class="btn btn-default jqmClose">解禁账号</div>
                    </div>
                </form>
              </div>
            </div>
        </div>
	</div>
</div>
</div>
    <div class="footer fcl">
        <div>
            <a href="#">关于本站</a> |
            <a href="#">联系我们</a>
        </div>
        <div>
            &copy; 2017
            <a href="index.html">一偏之荐</a>
            <a class="fcl" href="http://www.miitbeian.gov.cn">京ICP备17060481号-1</a>
        </div>
    </div>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/jqModal/1.4.2/jqModal.min.js"></script>
    <script src="js/common.js"></script>
    <script src="js/admin.js"></script>

</body>
</html>
