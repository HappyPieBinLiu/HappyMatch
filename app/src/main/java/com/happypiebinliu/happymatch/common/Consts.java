package com.happypiebinliu.happymatch.common;

/**
 * 静态变量
 * 多国语言的实现还未考虑？？预留项目
 * Created by Bin.Liu on 2016/10/14.
 */

public class Consts {

    /***
     * -----------------------------------UI-----------------------------------------
     ***/
    public static String REGISTER = "注册";
    public static String SHAREPREFER_FILE_USERINFO = "userInfo";
    public static String USER_NAME = "USER_NAME";
    public static String PASSWORD = "PASSWORD";
    public static String ISCHECK = "ISCHECK";

    /***-----------------------------DataBase-----------------------------------------***/
    /**
     * The name of the DataBase
     */
    public static final String DB_NAME = "HappyMatchDatabase";
    /**
     * The version of Database
     */
    public static final int VERSION = 1;
    /**
     * Table Name
     */
    public static final String USER_TABLE = "User";
    public static final String PASSWORD_TABLE = "Password";
    public static final String USER_NAME_FIELD = "user_name";
    public static final String USER_MAIL_FIELD = "user_mail";
    public static final String USER_SEX_FIELD = "user_sex";
    public static final String USER_HEIGHT_FIELD = "user_height";
    public static final String USER_SCALE_FIELD = "user_scale";
    public static final String PASSWORD_FIELD = "password";

    /**
     * 轮番图的判断
     * isDown 是否点击了轮番图，默认false
     * isRun 是否在运行，默认false
     */
    public static class Contant {
        public static boolean isDown;
        public static boolean isRun;
    }
    //--------Message-------------------------------------------------------
    public static final String TabLowItemIsEmpty = "TabLowItem 底部Ｔａｂ栏的内容为空！！";
}
