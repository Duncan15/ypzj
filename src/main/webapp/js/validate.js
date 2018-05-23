$(function(){
  $("#new-email").on('change', function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#email-alarm").text('');
    var value=$(this).val().trim();
    if(value==""){
      return;
    }else if(value.indexOf('@')==-1){
      $("#email-alarm").text('请输入邮箱账号');
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/detection?account='+value,
      type: 'GET',
      dataType: 'json',
    })
    .done(function(data) {
      console.log("success");
      if(data['errno']==205){
        $("#email-alarm").text('该账号已经存在');
        return;
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
  });
  $("#new-username").on('change', function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#nickname-alarm").text("");
    var value=$(this).val().trim();
    if(value==""){
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/detection?userName='+value,
      type: 'GET',
      dataType: 'json'
    })
    .done(function(data) {
      console.log("success");
      if(data['errno']==205){
        $("#nickname-alarm").text("该用户名已经存在");
      }
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
  });
  $("#new-password").on('change',function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#password-alarm").text("");
  });
})
function register(event){
  var flag=false;
  if($("#new-email").val().trim()==""){
    $("#email-alarm").text("邮箱账号不得为空");
    flag=true;
  }else {
    $("#new-email").val($("#new-email").val().trim());
  }
  if($("#new-username").val().trim()==""){
    $("#nickname-alarm").text("昵称不能为空");
    flag=true;
  }else {
    $("#new-username").val($("#new-username").val().trim());
  }
  if($("#new-password").val().trim()==""){
    $("#password-alarm").text("密码不得为空");
    flag=true;
  }else {
    $("#new-password").val($("#new-password").val().trim());
  }
  if(flag){
    event.preventDefault();
  }
}
