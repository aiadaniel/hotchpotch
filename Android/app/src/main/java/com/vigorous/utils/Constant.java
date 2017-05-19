package com.vigorous.utils;

/**
 * Created by lxm.
 */

public class Constant {
    public static final String SP_NAME = "Hotch";
    public static final String LAST_UID = "lastuid";
    public static final String LAST_NAME = "lastname";
    public static final String LAST_AVATAR = "lastavatar";
    public static final String LAST_TOKEN = "tok";

    public static final String BASE_URL = "http://10.32.17.99:8080/hotchpotchMVC/";//10.32.17.99  127.0.0.1
    public static final String HOTCH_USER = BASE_URL + "user/";
    public static final String HOTCH_POST = BASE_URL + "forum/post/";
    public static final String HOTCH_BOARD = BASE_URL + "forum/board/";
    public static final String HOTCH_CATEGORY = BASE_URL + "forum/category/";
    public static final String HOTCH_REPLY = BASE_URL + "forum/reply/";
    public static final String HOTCH_FILE = BASE_URL + "file/";

    public static String getHost() {
        String host = null;
        return host;
    }
}
