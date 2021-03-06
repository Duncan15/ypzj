$(function () {
  var $pictureModal=$("#picture-link");
  var $brocastModal=$("#brocast-link");
  var $linkModal=$("#link-link");
  $('#picture-modal').jqm();
  $("#brocast-modal").jqm();
  $("#link-modal").jqm();
  var $file=$("#file");
  var $pictureSaveBtn=$('#pic-save-btn');
  var $pictureExitBtn=$("#pic-exit-btn");
  var $brocastSaveBtn=$("#brocast-save-btn");
  var $brocastExitBtn=$("#brocast-exit-btn");
  var $linkSaveBtn=$("#link-save-btn");
  var $linkExitBtn=$("#link-exit-btn");
  $pictureModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $('#picture-modal').jqmShow();
  });
  $brocastModal.on('click', function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#brocast-modal").jqmShow();
  });
  $linkModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#link-modal").jqmShow();
  });
  $pictureSaveBtn.on('click',function(event) {
    event.preventDefault();
    if($file.val()==''){
      alert("请选择图片");
      return;
    }
    /* Act on the event */
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/img',
      dataType: 'JSON',
      type: 'POST',
      data: new FormData($("#pic-form")[0]),
      processData: false,
      contentType: false
    })
    .done(function(ans) {
      if(ans["errno"]==200){
        //删除上传的图片
        $file.after($file.clone().val(''));
        $file.remove();
        $file=$("#file");
        $pictureExitBtn.trigger('click');
        window.location.reload(true);
      }else {
        alert("图片上传失败！");
        $pictureExitBtn.trigger('click');
      }
    })
    .fail(function() {
      alert("出错，请重试。");
    })
    .always(function() {
      console.log("complete");
    });

  });
  $brocastSaveBtn.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var content=$("#brocast-content").val().trim();
    if(content==""){
      alert("内容不可为空");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/brocast',
      type: 'POST',
      dataType: 'json',
      data: {content: content}
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("公告发布成功！")
        window.location.reload(true);
      }else {
        alert("内部错误，公告发布失败！");
        $brocastExitBtn.trigger('click');
      }

    })
    .fail(function() {
      console.log("error");
      alert("未知错误，发布失败");
      $brocastExitBtn.trigger('click');
    })
    .always(function() {
      console.log("complete");
    });

  });
  $linkSaveBtn.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var intro=$("#describe").val().trim();
    var link=$("#link").val().trim();
    if(intro==""||link==""){
      alert("内容不得为空");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/friendLink',
      type: 'POST',
      dataType: 'json',contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        intro: intro,
        link:link
      })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("成功添加友链    "+data["data"]["newLink"]+"    !");
        window.location.reload(true);
      }else{
        commonUtility.dealAPI(data);
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });

  $("#ban-article-btn").on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var articleName=$("#article-title").val().trim();
    var articleAuthor=$("#article-author").val().trim();
    if(articleName==""||articleAuthor==""){
      alert("请输入内容");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/article',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        action:"ban",
        target:"article",
        name:articleName,
        author:articleAuthor
      })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("成功禁止文章!");
        window.location.reload(true);
      }else if(data["errno"]==206){
        alert("该文章已被封禁。");
      }else if(data["errno"]==202){
        alert("该文章不存在。");
      }else {
        commonUtility.dealAPI(data);
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
  $("#unban-article-btn").on('click', function(event) {
    event.preventDefault();
    /* Act on the event */
    var articleName=$("#article-title").val().trim();
    var articleAuthor=$("#article-author").val().trim();
    if(articleName==""||articleAuthor==""){
      alert("请输入内容");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/article',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        action:"unban",
        target:"article",
        name:articleName,
        author:articleAuthor
      })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("成功解禁文章!");
        window.location.reload(true);
      }else if(data["errno"]==207){
        alert("该文章已被解禁。");
      }else if(data["errno"]==202){
        alert("该文章不存在。");
      }else {
        commonUtility.dealAPI(data);
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
  $("#ban-account-btn").on('click', function(event) {
    event.preventDefault();
    /* Act on the event */
    var accountName=$("#account-name").val().trim();
    if(accountName==""){
      alert("请输入内容!");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/user',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        action:"ban",
        target:"user",
        name:accountName
      })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("成功禁止账号!");
        window.location.reload(true);
      }else if(data["errno"]==206){
        alert("该账号已被封禁。");
      }else if(data["errno"]==202){
        alert("该账号不存在。");
      }else {
        commonUtility.dealAPI(data);
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
  $("#unban-account-btn").on('click', function(event) {
    event.preventDefault();
    /* Act on the event */
    var accountName=$("#account-name").val().trim();
    if(accountName==""){
      alert("请输入内容!");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/admin/user',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        action:"unban",
        target:"user",
        name:accountName
      })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("成功解禁账号!");
        window.location.reload(true);
      }else if(data["errno"]==207){
        alert("该账号已被解禁。");
      }else if(data["errno"]==202){
        alert("该账号不存在。");
      }else {
        commonUtility.dealAPI(data);
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
});
