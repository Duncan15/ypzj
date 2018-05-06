package com.cwc.web.ypzj.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    private static ObjectMapper mapper=new ObjectMapper();
    public static String writeObject2String(Object o){
        String ans="";
        try {
            ans=mapper.writeValueAsString(o);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return ans;
    }
    public static Map<String,Object> readStirng2ObjectMap(String json){
        Map<String,Object> ans=null;
        try{
            ans=mapper.readValue(json,new TypeReference<Map<String,Object>>(){});
        }catch (IOException e){
            e.printStackTrace();
            ans=new HashMap<>();
        }
        return ans;
    }
    public static Map<String,String> readString2StringMap(String json){
        Map<String,String> ans=null;
        try {
            ans=mapper.readValue(json,new TypeReference<Map<String,String>>(){});
        }catch (IOException e){
            e.printStackTrace();
            ans=new HashMap<>();
        }
        return ans;
    }
}
