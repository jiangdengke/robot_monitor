/*
 * Decompiled with CFR 0.152.
 */
package com.robotmonitor.common.constant;

public class Constants {
    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String SUCCESS = "0";
    public static final String FAIL = "1";
    public static final String LOGIN_SUCCESS = "Success";
    public static final String LOGOUT = "Logout";
    public static final String REGISTER = "Register";
    public static final String LOGIN_FAIL = "Error";
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";
    public static final String ROBOT_LOGIN_TOKEN_KEY = "robot_login_tokens:";
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";
    public static final String RATE_LIMIT_KEY = "rate_limit:";
    public static final Integer CAPTCHA_EXPIRATION = 2;
    public static final String TOKEN = "token";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String LOGIN_USER_KEY = "login_user_key";
    public static final String JWT_USERID = "userid";
    public static final String JWT_USERNAME = "sub";
    public static final String JWT_AVATAR = "avatar";
    public static final String JWT_CREATED = "created";
    public static final String JWT_AUTHORITIES = "authorities";
    public static final String SYS_CONFIG_KEY = "sys_config:";
    public static final String SYS_DICT_KEY = "sys_dict:";
    public static final String RESOURCE_PREFIX = "/profile";
    public static final String LOOKUP_RMI = "rmi:";
    public static final String LOOKUP_LDAP = "ldap:";
    public static final String LOOKUP_LDAPS = "ldaps:";
    public static final String[] JOB_WHITELIST_STR = new String[]{"com.robotmonitor"};
    public static final String[] JOB_ERROR_STR = new String[]{"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml", "org.springframework", "org.apache", "com.robotmonitor.common.utils.file"};
}
