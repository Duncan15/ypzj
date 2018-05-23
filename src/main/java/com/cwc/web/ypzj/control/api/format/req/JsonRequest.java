package com.cwc.web.ypzj.control.api.format.req;

import com.cwc.web.ypzj.common.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/*
this request not parse header data from the HttpServletRequest
 */
public class JsonRequest {
    private String[] pathValue;
    private Map<String,String> queryMap;
    private String jsonBody;
    /*
    @param request
    @param pathValueNum point the pathValue number, 0 represent no parse pathValue
     */
    public JsonRequest(HttpServletRequest request,int pathValueNum){
        pathValue= RequestParser.parsePath(request.getRequestURI(),pathValueNum);
        queryMap=RequestParser.parseQuery(request.getQueryString());
        try{
            jsonBody=RequestParser.parseBody(request.getReader());
        }catch (IOException e){
            e.printStackTrace();
            jsonBody="";
        }
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public String[] getPathValue() {
        return pathValue;
    }

    public Map<String, String> getQueryMap() {
        return queryMap;
    }
}
