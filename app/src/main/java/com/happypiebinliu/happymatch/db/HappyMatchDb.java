package com.happypiebinliu.happymatch.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.happypiebinliu.happymatch.model.Password;
import com.happypiebinliu.happymatch.model.User;

import static com.happypiebinliu.happymatch.common.Consts.DB_NAME;
import static com.happypiebinliu.happymatch.common.Consts.PASSWORD_TABLE;
import static com.happypiebinliu.happymatch.common.Consts.USER_TABLE;
import static com.happypiebinliu.happymatch.common.Consts.VERSION;

/**
 * Created by B.Liu on 2016/10/18.
 */

public class HappyMatchDb {


    private static HappyMatchDb happyMatchDb;

    private static SQLiteDatabase sqLiteDatabase;

    /**
     * create a construction
     */
    public HappyMatchDb (Context context) {
        HappyMatchOpenHelper dbHelper = new HappyMatchOpenHelper(context, DB_NAME, null, VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * create a instance
     */
    public synchronized static HappyMatchDb getInstance (Context context) {
        if (happyMatchDb == null){
            happyMatchDb = new HappyMatchDb(context);
        }
        return  happyMatchDb;
    }
    /**
     * Sava the User's data。
     */
    public void saveUser(User user) {
        if (user != null) {
            ContentValues values = new ContentValues();
            values.put("user_name", user.getUserName());
            values.put("user_sex", user.getUserSex());
            values.put("user_mail", user.getUserMail());
            values.put("user_height", user.getUserHeight());
            values.put("user_scale", user.getUserScale());
            sqLiteDatabase.insert(USER_TABLE, null, values);
        }
    }
    public User selectUserInfo ( String userName){
        Cursor cursor = sqLiteDatabase.query("User", null, "user_name = ?",
                new String[] { String.valueOf(userName) }, null, null, null);
        User user = new User();
        if (cursor.moveToFirst()){
                user.setUserSex(cursor.getInt(cursor.getColumnIndex("user_sex")));
                user.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
                user.setUserMail(cursor.getString(cursor.getColumnIndex("user_mail")));
                user.setUserHeight(cursor.getString(cursor.getColumnIndex("user_height")));
                user.setUserScale(cursor.getString(cursor.getColumnIndex("user_scale")));
        }
        return user;
    }
    /**
     * Sava the Password's data。
     */
    public void savePassword(Password pwd) {
        if (pwd != null) {
            ContentValues values = new ContentValues();
            values.put("password", pwd.getPassword());
            values.put("user_name", pwd.getUserName());
            sqLiteDatabase.insert(PASSWORD_TABLE, null, values);
        }
    }
    public Password selectPasswordInfo ( String userName){
        Cursor cursor = sqLiteDatabase.query("Password", null, "user_name = ?",
                new String[] { String.valueOf(userName) }, null, null, null);
        Password password = new Password();
        if (cursor.moveToFirst()){
            password.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            password.setUserName(cursor.getString(cursor.getColumnIndex("user_name")));
        }
        return password;
    }
}
