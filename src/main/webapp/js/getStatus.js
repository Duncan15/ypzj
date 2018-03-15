$(function(){
  var $status=$(".status");
  $status.on('ready', function(event) {
    event.preventDefault();
    /* Act on the event */
    var webId=this.attr('id');
    $.ajax({
      url: '/crawlerManager/api/status?webId='+webId,
      type: 'GET',
      dataType: 'json',
    })
    .done(function(data) {
      console.log(data);
      var data=JSON.parse(data);
      console.log(data['status']);
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
    ajax
  });
  $status.trigger('click');
})
