package com.cwc.web.ypzj.control.api.format.format;

import com.sun.tools.javac.code.Attribute;

public class RespStruct {
    private Object data;
    private String errmsg;
    private Integer errno;
    public RespStruct(){}
    public RespStruct(Object data,int errno){
        this.data=data;
        this.errno=errno;
        this.errmsg=Errno.no2msgMap.get(errno);
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
        this.errmsg=Errno.no2msgMap.get(this.errno);
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
