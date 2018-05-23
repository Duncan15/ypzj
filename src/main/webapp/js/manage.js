$(function(){
  var $signatureModal=$("#signature-link");
  var $nicknameModal=$("#nickname-link");
  var $passwordModal=$("#password-link");
  $("#password-modal").jqm();
  $("#signature-modal").jqm();
  $("#nickname-modal").jqm();
  var signatureSaveBtn=$("#signature-save-btn");
  var signatureExitBtn=$("#signature-exit-btn");
  var nicknameSaveBtn=$("#nickname-save-btn");
  var nicknameExitBtn=$("#nickname-exit-btn");
  var passwordSaveBtn=$("#password-save-btn");
  var passwordExitBtn=$("#password-exit-btn");
  $signatureModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $('#signature-modal').jqmShow();
  });
  $nicknameModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#nickname-modal").jqmShow();
  });
  $passwordModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $("#password-modal").jqmShow();
  });
  signatureSaveBtn.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var signatureInput=$("#signature-content");
    var content=signatureInput.val().trim();
    if(content.length<3||content.length>100){
      alert("签名长度不得小于3或大于100");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+ 'api/user/manage/signature',
      type: 'PUT',
      dataType: 'json',
      data: JSON.stringify({signature: content})
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("签名修改成功.");
        signatureExitBtn.trigger('click');
        window.location.reload(true);
        return;
      }else{
        alert("出现错误，签名修改失败.");
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
  nicknameSaveBtn.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var nameInput=$("#nickname");
    var name=nameInput.val().trim();
    if(name.length<3||name.length>20){
      alert("昵称长度不得小于3或大于20");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/user/manage/nickname',
      type: 'PUT',
      dataType: 'json',
      data: JSON.stringify({nickname: name})
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("昵称修改成功.");
        passwordExitBtn.trigger('click');
        window.location.reload(true);
        return;
      }else {
        alert("内部错误，昵称修改失败.");
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
  passwordSaveBtn.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var passFirstInput=$("#first-pass");
    var passSecondInput=$("#second-pass");
    var password=passFirstInput.val().trim();
    if(passFirstInput.val().trim()!==passSecondInput.val().trim()){
      alert("两次输入密码不一致，修改失败.");
      passFirstInput.val("");
      passSecondInput.val("");
      passwordExitBtn.trigger('click');
      return;
    }
    if(password.length<6||password.length>9){
      alert("密码长度不得小于6或大于9");
      return;
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/user/manage/password',
      type: 'PUT',
      dataType: 'json',
      contentType:"application/json;charset=utf-8",
      data: JSON.stringify({password:password })
    })
    .done(function(data) {
      console.log("success");
      if(data["errno"]==200){
        alert("密码修改成功.");
        passwordExitBtn.trigger('click');
        return;
      }else {
        alert("内部错误，密码修改失败.");
        return;
      }

    })
    .fail(function() {
      console.log("error");
      alert("内部错误，密码修改失败.");
      return;
    })
    .always(function() {
      console.log("complete");
    });

  });
})
