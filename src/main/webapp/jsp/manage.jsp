<%@ page import="com.cwc.web.ypzj.model.obj.User" %><%--
  Created by IntelliJ IDEA.
  User: cwc
  Date: 2018/5/6
  Time: 下午2:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" type="text/css" href="../node_modules/bootstrap/dist/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../node_modules/jqmodal/examples/jqModal.css">
    <link rel="stylesheet" type="text/css" href="../css/define.css">
    <link rel="icon" type="image/x-icon" href="../img/favicon.ico">
    <title>一偏之荐</title>
    <style>
        body {
            position: relative;
        }
        ul.nav-pills {
            top: 100px;
            position: fixed;
        }
        .info-bar {
            padding: 20px 10px;
        }
        .tac {
            line-height: 200%;
        }
        .sidebar-item {
            padding: 20px 20px;
        }
        .content-item {
            padding: 50px 20px;
        }
        li.active a{
          font-weight:bold;
        	background: rgba(0,0,0,0.04)!important;
          color:#333!important;
        }
        li a {
          color: #333;
        }

        #signature-content {
          width: 93%;
          margin: 0 20px;
          border-radius: 5px;
          height: 100px;
        }
    </style>
</head>
<%User usr=(User)session.getAttribute("currentUser");%>
<body data-spy="scroll" data-target="#myScrollspy" data-offset="80">
<jsp:include page="/WEB-INF/jsp/part/navbar.jsp">
    <jsp:param name="prefix" value="../"/>
</jsp:include>

<div class="main-panel container">
    <div class="col-sm-3 nav" id="myScrollspy">
        <ul class="nav nav-pills nav-stacked">
            <li class="sidebar-item fwl fcb fsb active"><a href="#password-manage">密码管理</a></li>
            <li class="sidebar-item fwl fcb fsb"><a href="#nickname-manage">昵称管理</a></li>
            <li class="sidebar-item fwl fcb fsb"><a href="#signature-manage">签名管理</a></li>
            <li class="sidebar-item fwl fcb fsb"><a href="">关注管理</a></li>
            <li class="sidebar-item fwl fcb fsb"><a href="">文章管理</a></li>
        </ul>
    </div>
    <div class="col-sm-9">
        <div class="modal-group">
          <div id="password-modal" class="jqmWindow">
            <div class="modal-title">修改密码</div>
            <form class="form-horizontal">
                <div class="form-group modal-group">
                  <label class="col-xs-4 control-label">密码</label>
                  <div class="col-xs-8">
                    <input id="first-pass" required type="password" class="form-control" name="password" value="">
                  </div>
                </div>
                <div class="form-group modal-group">
                  <label class="col-xs-4 control-label">再次输入</label>
                  <div class="col-xs-8">
                    <input id="second-pass" required type="password" class="form-control" name="password" value="">
                  </div>
                </div>
                <div class="btn-group btn-group-sm btn-center">
                  <div id="password-save-btn" class="btn btn-default">保存</div>
                  <div id="password-exit-btn" class="btn btn-default jqmClose">关闭</div>
                </div>
            </form>
          </div>
          <div id="nickname-modal" class="jqmWindow">
            <div class="modal-title">编辑昵称</div>
            <form class="form-horizontal">
                <div class="form-group modal-group">
                  <label class="col-xs-2 control-label">昵称</label>
                  <div class="col-xs-10">
                    <input required type="text" class="form-control" id="nickname" value="">
                  </div>
                </div>
                <div class="btn-group btn-group-sm btn-center">
                  <div id="nickname-save-btn" class="btn btn-default">保存</div>
                  <div id="nickname-exit-btn" class="btn btn-default jqmClose">关闭</div>
                </div>
            </form>
          </div>
          <div id="signature-modal" class="jqmWindow">
            <div class="modal-title">编辑签名</div>
            <form id="signature-form" class="form-horizontal">
              <div class="form-group modal-group">
                <textarea id="signature-content" class="content-input"></textarea>
              </div>
              <div class="form-group">
                <div class="btn-group btn-group-sm btn-center">
                  <button id="signature-save-btn" class="btn btn-default">保存</button>
                  <div id="signature-exit-btn" class="btn btn-default jqmClose">关闭</div>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="list-group basic-info info-bar">
            <div class="list-group-item content-item tac fwl fcb fssm">
                基本信息
            </div>
            <div class="list-group-item content-item clearfix" id="mail">
                <div class="col-sm-3 tac fwl fcb fsb">
                    邮箱
                </div>
                <div class="col-sm-6 tac fwn fcb fsb">
                    <%=usr.getAccount()%>
                </div>
            </div>
            <div class="list-group-item content-item clearfix" id="password-manage">
                <div class="col-sm-3 tac fwl fcb fsb">
                    密码
                </div>
                <div class="col-sm-6 tac fwn fcb fsb">
                </div>
                <div class="col-sm-3 fwl fcb fsb btn btn-default" id="password-link">
                    修改密码
                </div>
            </div>
            <div class="list-group-item content-item clearfix" id="nickname-manage">
                <div class="col-sm-3 tac fwl fcb fsb">
                    昵称
                </div>
                <div class="col-sm-6 tac fwn fcb fsb">
                    <%=usr.getUserName()%>
                </div>
                <div class="col-sm-3 fwl fcb fsb btn btn-default" id="nickname-link">
                    修改昵称
                </div>
            </div>
            <div class="list-group-item content-item clearfix" id="signature-manage">
                <div class="col-sm-3 tac fwl fcb fsb">
                    签名
                </div>
                <div class="col-sm-6 tac fwn fcb fsb">
                    人生若只如初见，何事秋风悲画扇
                </div>
                <div class="col-sm-3 fwl fcb fsb btn btn-default" id="signature-link">
                    修改签名
                </div>
            </div>
        </div>
        <div class="list-group social-info info-bar">
            <div class="list-group-item content-item tac fwl fcb fssm">
                社交信息
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
<script src="../js/global-param.js"></script>
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/jqModal/1.4.2/jqModal.min.js"></script>
<script src="../js/manage.js"></script>
</body>
</html>
