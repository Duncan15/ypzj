package com.cwc.web.common.api.format;

public class RespStruct {
    private Object data;
    private String errmsg;
    private Integer errno;
    public RespStruct(Object data,int errno){
        this.data=data;
        this.errno=errno;
        this.errmsg=Errno.no2msgMap.get(errno);
    }

    public Object getData() {
        return data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public Integer getErrno() {
        return errno;
    }
}
