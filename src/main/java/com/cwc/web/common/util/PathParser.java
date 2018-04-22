package com.cwc.web.common.util;

public class PathParser {
    public static String[] parse(String uri,int paramNum){
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
}
