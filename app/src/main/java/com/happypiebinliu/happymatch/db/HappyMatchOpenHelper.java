package com.happypiebinliu.happymatch.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.happypiebinliu.happymatch.common.Consts.PASSWORD_TABLE;
import static com.happypiebinliu.happymatch.common.Consts.USER_TABLE;

/**
 * Created by B.Liu on 2016/10/18.
 */

public class HappyMatchOpenHelper extends SQLiteOpenHelper {

    /**
     * Created [user] table
     */
    public static final String CREATE_USER_TABLE = "create table if not exists "
            + USER_TABLE + "("
            + "id integer primary key autoincrement,"
            + "user_name text,"
            + "user_sex int,"
            + "user_mail text,"
            + "user_height int,"
            + "user_scale text)";


    /**
     * Created [password] table
     */
    public static final String CREATE_PASSWORD_TABLE = "create table if not exists "
            + PASSWORD_TABLE + "("
            + "id integer primary key autoincrement,"
            + "password text,"
            + "user_name text)";

    /**
     * Drop [User] table
     */
    public static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + USER_TABLE;

    /**
     * Drop [password] table
     */
    public static final String DROP_PASSWORD_TABLE = "DROP TABLE IF EXISTS " + PASSWORD_TABLE;


    public HappyMatchOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create the user table
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        // create the password table
        sqLiteDatabase.execSQL(CREATE_PASSWORD_TABLE);
    }

    /**
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                onCreate(sqLiteDatabase);
            case 2:
                // drop the exists table
                sqLiteDatabase.execSQL(DROP_USER_TABLE);
                sqLiteDatabase.execSQL(DROP_PASSWORD_TABLE);
                // recreate table
                onCreate(sqLiteDatabase);
            default:
        }
    }
}
