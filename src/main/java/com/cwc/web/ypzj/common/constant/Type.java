package com.cwc.web.ypzj.common.constant;

public class Type {
    public enum  MessageType{
        ARTICLE_COMMENT((byte)1),
        USER_COMMENT((byte)2);
        private byte value;
        MessageType(byte value){
            this.value=value;
        }
        public byte getValue(){
            return this.value;
        }
    }
    public enum  Order{
        DESC,
        ASC
    }
    public enum  ContentStatus{
        BAN((byte)1),
        UNBAN((byte) 0);
        private byte value;
        ContentStatus(byte value){
            this.value=value;
        }

        public byte getValue() {
            return value;
        }
    }
    public enum UserStatus{
        VALID((byte)1),
        INVALID((byte)0);
        UserStatus(byte value){
            this.value=value;
        }
        private byte value;
        public byte getValue(){
            return value;
        }
    }
}
