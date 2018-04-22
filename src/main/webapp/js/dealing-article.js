$(function(){
  var parser=$("#editor").textParser();
  var $title=$("#title");
  var $labelInput=$(".label-input");
  var $hdLabelInput=$("#hd-label-input");
  var $preview=$("#preview");
  var $create=$("#create");
  var $cancel=$("#cancel");
  $(".tag").on('click', function(event) {
    event.preventDefault();
    /* Act on the event */
    var $this=$(this);
    if($this.hasClass('fcl')){
      $this.removeClass('fcl');
      $labelInput.html('');
      $hdLabelInput.val('');
    }
    else{
    	$('.tag').removeClass('fcl');
      $this.addClass('fcl');
      $labelInput.html('<div class="label label-info">'+$this.text()+'</div>');
      $hdLabelInput.val($this.attr('label-id'));
    }
  });
  $create.on('click',function(event) {
    event.preventDefault();
    /* Act on the event */
    var title=$title.val().trim();
    var content=parser.parse().trim();
    var label=$hdLabelInput.val().trim();
    $.ajax({
      url: baseUrl+'article',
      type: 'POST',
      dataType: 'JSON',
      data: {
        title:title,
        content:content,
        labelId:label
      }
    })
    .done(function() {
      window.location.replace(baseUrl+"user/personal");
    })
    .fail(function() {
      alert("新建文章出错");
    })
    .always(function() {
      console.log("complete");
    });

  });
});
