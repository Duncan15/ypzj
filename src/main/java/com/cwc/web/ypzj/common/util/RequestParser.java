package com.cwc.web.ypzj.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    public static String[] parsePath(String uri,int paramNum){
        String[] ans=new String[paramNum];
        if(uri.endsWith("/")){
            uri=uri.substring(0,uri.length()-1);
        }
        try{
            for(int i=paramNum-1;i>=0;i--){
                ans[i]=uri.substring(uri.lastIndexOf("/")+1,uri.length());
                uri=uri.substring(0,uri.lastIndexOf("/"));
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }
        return ans;
    }
    public static Map<String,String> parseQuery(String queryString){
        Map<String,String> queryMap=new HashMap<>();
        if (queryString==null)return queryMap;
        for(String each:queryString.split("&")){
            String[] kAndV=each.split("=");
            if(kAndV.length!=2){
                continue;
            }
            String v=queryMap.get(kAndV[0]);
            if(v!=null){
                queryMap.put(kAndV[0],v+","+kAndV[1]);
            }else {
                queryMap.put(kAndV[0],kAndV[1]);
            }
        }
        return queryMap;
    }
    public static String parseBody(BufferedReader reader){
        StringBuffer sb=new StringBuffer();
        try{
            String line="";
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
