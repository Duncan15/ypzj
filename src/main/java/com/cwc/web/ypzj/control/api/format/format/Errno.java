package com.cwc.web.ypzj.control.api.format.format;

import java.util.HashMap;

public class Errno {
    public static final int SUCCESS=200;
    public static final int NODATA=201;
    public static final int NOEXIST=202;
    public static final int PARAMERR=203;
    public static final int NOAUTHORITY=204;
    public static final int HASEXIST=205;
    public static final int BAN=206;
    public static final int UNBAN=207;
    public static final int NOFOUND=404;
    public static final int SYSERR=500;
    public static final HashMap<Integer,String> no2msgMap=new HashMap<>();
    static {
        no2msgMap.put(SUCCESS,"run successfully");
        no2msgMap.put(NODATA,"there is no data to find");
        no2msgMap.put(NOEXIST,"the target data has not existed");
        no2msgMap.put(PARAMERR,"the input data can't fit the predefined format");
        no2msgMap.put(SYSERR, "the server occurs an error");
        no2msgMap.put(NOAUTHORITY,"you have no authority to access this resource");
        no2msgMap.put(BAN,"the target data has been baned");
        no2msgMap.put(UNBAN,"the target data hasn't been baned");
        no2msgMap.put(NOFOUND,"no this resource");
        no2msgMap.put(HASEXIST,"the target obj has exist");
    }
}
