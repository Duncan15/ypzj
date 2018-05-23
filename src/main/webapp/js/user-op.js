$(function(){
  $("#recommend-btn").on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var articleId=parseInt($("#articleId").text().trim());
    $.ajax({
      url: commonUtility.baseUrl+'api/user/support',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({articleId:articleId})
    })
    .done(function(data) {
      console.log("success");
      commonUtility.dealAPI(data);
      if(data["errno"]==200){
        alert("推荐成功");
        window.location.reload(true);
      }else if(data["errno"]==500){
        alert("内部错误，推荐失败");
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
  });
  $("#comment-btn").on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var hostId=parseInt($("#hostId").text().trim());
    var messageType=parseInt($("#messageType").text().trim());
    var senderId=parseInt($("#senderId").text().trim());
    var comment=$("#comment-content").val().trim();
    if(comment==""){
      alert("内容不能为空!");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/user/comment',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        hostId:hostId,
        messageType:messageType,
        senderId:senderId,
        comment:comment
      })
    })
    .done(function(data) {
      console.log("success");
      commonUtility.dealAPI(data);
      if(data["errno"]==200){
        alert("评论成功");
        $("#comment-content").val("");
        flushCommentList(1,10);
        //window.location.reload(true);
      }else if(data["errno"]==500) {
        alert("内部错误，评论失败");
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
  $("#reply-save-btn").on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var topCommentId=$("#tmpTopCommentId").text();
    var receiverId=$("#tmpReceiverId").text();
    var senderId=$("#senderId").text();
    var comment=$("#reply-content").val().trim();
    var hostId=parseInt($("#hostId").text().trim());
    var messageType=parseInt($("#messageType").text().trim());
    if(comment==""){
      alert("内容不能为空!");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/user/comment',
      type: 'POST',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({
        hostId:hostId,
        messageType:messageType,
        senderId:parseInt(senderId),
        comment:comment,
        topCommentId:parseInt(topCommentId),
        receiverId:parseInt(receiverId)
      })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("回复成功");
        $("#reply-content").val("");
        $("#reply-exit-btn").trigger('click');
        flushCommentList(1,10);
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
  $("#attention-btn").on("click",function (event) {
      var authorIdStr=$("#authorId").text().trim();
      event.preventDefault();
      $.ajax({
        url: commonUtility.baseUrl+'api/user/attention',
        type: 'POST',
        dataType: 'json',
        contentType:"application/json;charset=utf-8",
        data: {targetUserId: authorIdStr}
      })
      .done(function(data) {
        console.log("success");
        if(data["errno"]==200){
          alert("成功关注 "+data["data"]["targetUserName"]+" !");
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

  })
});
