package edu.haut.greenhouse.common.util;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ValidateUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ValidateUtils.class);

    public static final int USERNAME_MIN_CHARS = 1, USERNAME_MAX_CHARS = 30;

    public static final int PASSWORD_MIN_CHARS = 5, PASSWORD_MAX_CHARS = 30;

    public static final String EMAIL_PATTERN = "^[\\w-+]+(\\.[\\w-+]+)*@([\\w-]+\\.)+[a-zA-Z]+$";
    
    public static final String TELEPHONE = "^(0[0-9]{2,3}\\-)?([1-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";

    public static final String MOBILE = "^1[3|4|5|7|8][0-9]\\d{4,8}$";
    
    public static final String INTEGER = "^[1-9]+[0-9]*$";
    
    public static boolean validateUsername(String username) {
        if (username == null) {
            return false;
        }
        for (int i = 0; i < username.length(); i++) {
            if (Character.isWhitespace(username.charAt(i))) {
                log.debug("Has white space: " + username);
                return false;
            }
        }
        return username.length() >= USERNAME_MIN_CHARS
                && username.length() <= USERNAME_MAX_CHARS;
    }

    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= PASSWORD_MIN_CHARS
                && password.length() <= PASSWORD_MAX_CHARS;
    }

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        return email == null ? false : Pattern.matches(EMAIL_PATTERN, email);
    }
    
    /**
     * 校验手机号
     * @param mobile
     * @return
     */
    public static boolean validateMobile(String mobile) {
       return mobile == null ? false : Pattern.matches(MOBILE, mobile);
    }
    
    /**
     * 校验电话号码：可带分机号
     * @param telephone
     * @return
     */
    public static boolean validateTelephone(String telephone) {
    	return telephone == null ? false : Pattern.matches(TELEPHONE, telephone);
    }
    
    /**
     * 校验输入的正整数
     * @param intNum
     * @return
     */
    public static boolean validateInteger(String intNum) {
    	return intNum == null ? false : Pattern.matches(INTEGER, intNum);
    }
    

}
