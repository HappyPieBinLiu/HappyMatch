package com.happypiebinliu.happymatch.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.common.Consts;
import com.happypiebinliu.happymatch.common.ViewUtil;
import com.happypiebinliu.happymatch.db.HappyMatchDb;
import com.happypiebinliu.happymatch.db.HappyMatchOpenHelper;
import com.happypiebinliu.happymatch.model.User;

import static com.happypiebinliu.happymatch.common.Consts.DB_NAME;
import static com.happypiebinliu.happymatch.common.Consts.PASSWORD_TABLE;
import static com.happypiebinliu.happymatch.common.Consts.USER_TABLE;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private HappyMatchOpenHelper dbHelper;
    private ProgressDialog progressDialog;
    private HappyMatchDb happyMatchDb;
    private Button btnRegister;
    private Button btnCancelReg;
    private EditText userName;
    private EditText password;
    private EditText passwordAgain;
    private EditText mailAddress;
    private Spinner spinner;
    private String sex;
    private EditText height;
    private EditText scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // no title
        this.getSupportActionBar().hide();
        setContentView(R.layout.register);
        dbHelper = new HappyMatchOpenHelper(this, DB_NAME, null, 1);
        // init view
        userName = getViewInfo(R.id.userName);
        password = getViewInfo(R.id.regPwd);
        passwordAgain = getViewInfo(R.id.regPwdAgain);
        mailAddress = getViewInfo(R.id.regMail);
        height = getViewInfo(R.id.regInfoheight);
        scale = getViewInfo(R.id.regInfoScale);
        spinner = (Spinner) findViewById(R.id.regSex);
        sex = (String) spinner.getSelectedItem();

        // init onClickListener of the Button
        btnRegister = getViewInfo(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        btnCancelReg = getViewInfo(R.id.btnCancelReg);
        btnCancelReg.setOnClickListener(this);

        // happyMatchDb
        happyMatchDb = HappyMatchDb.getInstance(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btnRegister:
                // Check the input information
                if (inputCheckReg()){
                    userInfoInsert();
                    intent = new Intent(RegisterActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btnCancelReg:
                // back to login activity
                intent = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void userInfoInsert() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // transaction start
        db.beginTransaction();
        try {
            // insert the user information
            ContentValues values = new ContentValues();
            if (!TextUtils.isEmpty(userName.getText())) values.put(Consts.USER_NAME_FIELD, userName.getText().toString());
            if (!TextUtils.isEmpty(mailAddress.getText()))  values.put(Consts.USER_MAIL_FIELD, mailAddress.getText().toString());
            if (!TextUtils.isEmpty(sex)) values.put(Consts.USER_SEX_FIELD, sex);
            if (!TextUtils.isEmpty(height.getText())) values.put(Consts.USER_HEIGHT_FIELD, height.getText().toString());
            if (!TextUtils.isEmpty(scale.getText())) values.put(Consts.USER_SCALE_FIELD, scale.getText().toString());
            db.insert(USER_TABLE, null, values);
            values.clear();
            // insert the user's password information
            if (!TextUtils.isEmpty(userName.getText())) values.put(Consts.USER_NAME_FIELD, userName.getText().toString());
            if (!TextUtils.isEmpty(password.getText())) values.put(Consts.PASSWORD_FIELD, password.getText().toString());
            db.insert(PASSWORD_TABLE, null, values);
            values.clear();
            db.setTransactionSuccessful();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            // transaction end
            db.endTransaction();
        }
    }

    /***
     * before of register ,  check the input information
     */
    private Boolean inputCheckReg() {
        // required fields check
        if (TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(password.getText())
                    ||TextUtils.isEmpty(passwordAgain.getText()) || TextUtils.isEmpty(mailAddress.getText())) {
            Toast.makeText(this, R.string.tip_null, Toast.LENGTH_LONG).show();
            return false;
        }
        // userName is not be registered
        User user = happyMatchDb.selectUserInfo(userName.getText().toString().trim());
        if (user.getUserName() != null) {
            Toast.makeText(this, R.string.tip_user_is_registered, Toast.LENGTH_LONG).show();
            return false;
        }
        // the twice input password is not same
        if (!(password.getText().toString().trim()).equals(passwordAgain.getText().toString().trim())) {
            Toast.makeText(this, R.string.tip_password_not_same, Toast.LENGTH_LONG).show();
            return false;
        }
        // mail address format check
        if (!Patterns.EMAIL_ADDRESS.matcher(mailAddress.getText()).matches()){
            Toast.makeText(this, R.string.tip_mail_format, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    /***
     * findViewById common
     */
    public <E extends View> E getViewInfo(int resId) {
        return ViewUtil.findViewById(this, resId);
    }
}
