package com.happypiebinliu.happymatch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.happypiebinliu.happymatch.R;
import com.happypiebinliu.happymatch.db.HappyMatchDb;
import com.happypiebinliu.happymatch.model.Password;
import com.happypiebinliu.happymatch.model.User;

import static com.happypiebinliu.happymatch.common.Consts.REGISTER;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtRegisterLink;
    private Intent intent;
    private AutoCompleteTextView userName;
    private EditText password;
    private HappyMatchDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No title
        //requestWindowFeature(Window.FEATURE_NO_TITLE); if extends Activity you can use it
        // if you extends AppCompatActivity, can use ActionBar.In the Activity,it is titleBar.
        this.getSupportActionBar().hide();
        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Register Link
        setContentView(R.layout.sign_in);

        // the invent of click text link
        txtRegisterLink = (TextView) findViewById(R.id.txtRegisterLink);
        String str2 = REGISTER;
        SpannableString spans = new SpannableString(str2);
        spans.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }, 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtRegisterLink.setMovementMethod(LinkMovementMethod.getInstance());
        txtRegisterLink.setText(spans);

        // get userName
        userName = (AutoCompleteTextView) findViewById(R.id.username);
        // get password
        password = (EditText) findViewById(R.id.password);

        // Button ----Login Button
        Button loginButton = (Button) findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(this);
        // Button ----Forget the password Button
        Button forgetPwButton = (Button) findViewById(R.id.btnForgetPwd);
        forgetPwButton.setOnClickListener(this);

        // Db init
        db = HappyMatchDb.getInstance(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            // login
            case R.id.btnLogin:
                // user account is right, move to the main activity
                if (checkLoginInput()) {
                    intent = new Intent(SignInActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btnForgetPwd:
                // find the password
                intent = new Intent(SignInActivity.this, FindPasswordActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private boolean checkLoginInput(){

        // userName can not be null.
        if (TextUtils.isEmpty((CharSequence) userName)){
            Toast.makeText(this, R.string.tip_username_null, Toast.LENGTH_LONG).show();
            return false;
        }
        // password can not be null.
        if (TextUtils.isEmpty((CharSequence) password)){
            Toast.makeText(this, R.string.tip_password_null, Toast.LENGTH_LONG).show();
            return false;
        }
        //  is a registered user account?
        User user = db.selectUserInfo(String.valueOf(userName));
        if (user.getUserName() != null && !"".equals(user.getUserName())) {
            // password
            Password pwd = db.selectPasswordInfo(user.getUserName());
            // password is wrong
            if (!pwd.getPassword().equals(password.toString())){
                Toast.makeText(this, R.string.tip_username_wrong, Toast.LENGTH_LONG).show();
                return false;
            }
        }else {
            // the account is not register
            Toast.makeText(this, R.string.tip_password_wrong, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
