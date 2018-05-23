$(function(){
  var $editor=$("#editor");
  var $pictureModal=$("#picture-link");
  var $linkModal=$("#link-link");
  $('#picture-modal').jqm();
  $('#link-modal').jqm();

  var $file=$("#file");
  var $pictureSaveBtn=$('#pic-save-btn');
  var $pictureExitBtn=$("#pic-exit-btn");

  var $describe=$("#describe");
  var $link=$("#link");
  var $linkSaveBtn=$("#link-save-btn");
  var $linkExitBtn=$("#link-exit-btn");
  $linkModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $('#link-modal').jqmShow();
  });
  $pictureModal.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    $('#picture-modal').jqmShow();
  });
  $pictureSaveBtn.on('click',function(event) {
    event.preventDefault();
    if($file.val()==''){
      alert("请选择图片");
      return;
    }
    /* Act on the event */
    $.ajax({
      url: commonUtility.baseUrl+'api/user/img',
      dataType: 'JSON',
      type: 'POST',
      data: new FormData($("#pic-form")[0]),
      processData: false,
      contentType: false
    })
    .done(function(ans) {
      var list=ans["responseList"];
      var flag=true;
      for(var each in list){
        if(list[each].opCode==0){
          console.log("上传失败");
          flag=false;
          continue;
        }
        else if(list[each].opCode==1){
          console.log("上传成功");

        }
        else if(list[each].opCode==2){
          console.log("该图片已存在");

        }
        //插入图片
        $editor.insertContent('$图片'+list[each].imageAddr+'$\n');
        //删除上传的图片
        $file.after($file.clone().val(''));
        $file.remove();
        $file=$("#file");
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
  $linkSaveBtn.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var desStr=$describe.val().trim();
    var linkStr=$link.val().trim();
    if(desStr==""&&linkStr==""){
      alert("请输入文字和链接");
      return;
    }
    if(!linkStr.startsWith('http')){
      linkStr="http://".concat(linkStr);
    }
    $.ajax({
      url: commonUtility.baseUrl+'api/user/link',
      type: 'POST',
      dataType: 'json',
      data: {
        intro: desStr,
        link: linkStr
      }
    })
    .done(function(ans) {
      console.log("success");
      if(ans.opCode==0){
        alert("失败，请重试。");
        return;
      }
      //插入链接
      $editor.insertContent('$链接'+linkStr+'*'+ans.rowId+'@'+desStr+'$\n');
      //删除输入栏
      $describe.val('');
      $link.val('');
      $linkExitBtn.trigger('click');
    })
    .fail(function() {
      alert("出错，请重试。");
    })
    .always(function() {
      console.log("complete");
    });

  });
});
