package edu.haut.greenhouse.common.util;

public class JsonStatus {
    
	// config
	public static final String CHARSET = "utf-8";
	public static final String VERSION = "0.0.1";
    //properties name
    
    public static final String STATUS = "status";
    
    public static final String MSG = "msg";
    
    public static final String RESULT = "result";
    
    //status
    
    public static final int EMPTY = 1;
    
    public static final int ERROR = 0;
    
    public static final int SUCCESS = 200;
    
    public static final int SERVER_ERROR = 500;
    
    public static final String SERVER_ERROR_MSG = "服务器异常";
    
    //data not empty error msg 
    public static final String unGrantUpload = "未经授权图片上传被拒绝";
    
    public static final String grantFailed = "授权失败";
    
    public static final String grantCodeExpired = "未登陆或无权限操作";
    
    public static final String grantCodeEmptyMsg = "授权码不能为空！";
    
    public static final String provinceCodeEmptyMsg = "省份代码不能为空！";
    
    public static final String cityCodeEmptyMsg = "城市代码不能为空！";
    
    public static final String districtCodeEmptyMsg = "区县代码不能为空！";
    
    public static final String idNumberEmptyMsg = "身份证号不能为空！";
    
    public static final String realNameEmptyMsg = "真实姓名不能为空！";
    
    public static final String fullAddressEmptyMsg = "详细地址不能为空！";
    
    public static final String contactEmptyMsg = "联系方式不能为空！";
    
    public static final String defaultAddressEmptyMsg = "默认地址选择不能为空！";
    
    public static final String deleteErrorMsg = "删除失败";
    
    public static final String commonIdEmpty = "Id不能为空";
    
    public static final String uploadErrorMsg = "图片上传失败";
    
    public static final String deviceIdEmptyMsg = "设备Id不能为空";
    
    public static final String piccode_msg_error = "图形验证码错误" ;
    
    public static final String phonecode_msg_error= "手机验证码错误" ;
    
    public static final String notExitResource = "请求资源不存在";
    
    public static final String qrcodeContentEmptyMsg = "二维码内容为空";

    public static final String qrcodeEmptyMsg = "二维码生成失败";
    
}