$(function(){
  var target=$("#target").text().trim();
  if(target=="login"){
    window.setTimeout(function(){
      window.location.replace(commonUtility.baseUrl+"login");
    },5000);
  }else if (target=="index") {
    window.setTimeout(function(){
      window.location.replace(commonUtility.baseUrl+"index");
    },5000);
  }
});
