package com.lostad.app.base.util;
public class FormFile {  
    /* 上传文件的数据 */  
    private byte[] data;  
    /* 文件名称 */  
    private String filname;  
    /* 表单字段名称*/  
    private String formname;  
    /* 内容类型 */  
    private String contentType = "application/octet-stream"; //需要查阅相关的资料  
      
    public FormFile(String filname, byte[] data, String formname, String contentType) {  
        this.data = data;  
        this.filname = filname;  
        this.formname = formname;  
        if(contentType!=null) this.contentType = contentType;  
    }  
  
    public byte[] getData() {  
        return data;  
    }  
  
    public void setData(byte[] data) {  
        this.data = data;  
    }  
  
    public String getFilname() {  
        return filname;  
    }  
  
    public void setFilname(String filname) {  
        this.filname = filname;  
    }  
  
    public String getFormname() {  
        return formname;  
    }  
  
    public void setFormname(String formname) {  
        this.formname = formname;  
    }  
  
    public String getContentType() {  
        return contentType;  
    }  
  
    public void setContentType(String contentType) {  
        this.contentType = contentType;  
    }  
      
}  