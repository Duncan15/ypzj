package com.cwc.web.common.api.format;

import java.util.HashMap;

public class Errno {
    public static final int SUCCESS=200;
    public static final int NODATA=201;
    public static final int NOEXIST=202;
    public static final int PARAMERR=203;
    public static final int SYSERR=500;
    public static final HashMap<Integer,String> no2msgMap=new HashMap<>();
    static {
        no2msgMap.put(SUCCESS,"run successfully");
        no2msgMap.put(NODATA,"there is no data to find");
        no2msgMap.put(NOEXIST,"the target data has not existed");
        no2msgMap.put(PARAMERR,"the input data can't fit the predefined format");
        no2msgMap.put(SYSERR, "the server occurs an error");
    }
}
