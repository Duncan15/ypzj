var commonUtility=(function() {
  var baseUrl="/";
  var id2DesMap={
    200:"run successfully",
    201:"there is no data to find",
    202:"the target data has not existed",
    203:"the input data can't fit the predefined format",
    204:"you have no authority to access this resource",
    205:"the target obj has existed",
    404:"no this resource",
    500:"the server occurs an error"
  };
  function dealAPI(data) {
    if(data==undefined||data==null){
      console.log("data format error");
      return;
    }
    if(data["errno"]==204){
      console.log(id2DesMap[204]);
      window.location.replace(baseUrl+"login");
    }
  }
  return{
    dealAPI:dealAPI,
    baseUrl:baseUrl
  }
})();
