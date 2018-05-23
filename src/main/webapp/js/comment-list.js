$(function(){
  flushCommentList(1,10);
});
function flushCommentList(pageCount,pageSize){
  var hostId=$("#hostId").text().trim();
  var messageType=$("#messageType").text().trim();
  var JQobj=$("#comment-list");
  $.ajax({
    url: commonUtility.baseUrl+'api/comment?hostId='+hostId+"&messageType="+messageType+"&pageCount="+pageCount+"&pageSize="+pageSize,
    type: 'GET',
    dataType: 'json'
  })
  .done(function(data) {
    console.log("success");
    var commentListTmpl=template("comment-list-tmpl",data.data);
    JQobj.html(commentListTmpl);
    $(".reply-btn").on('click',function(event) {
      event.preventDefault();
      /* Act on the event */
      var $this=$(this);
      var $comment=$this.parents(".comment");
      var topCommentId=$comment.find(".topCommentId").text().trim();
      var senderId=$comment.find(".senderId").text().trim();
      $("#tmpTopCommentId").text(topCommentId);
      $("#tmpReceiverId").text(senderId);
      $("#reply-modal").jqm();
      $("#reply-modal").jqmShow();
    });
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}
