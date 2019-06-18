package com.example.commoditymanagementsystem.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Rergistered extends AppCompatActivity {
    private EditText mReaccount;
    private EditText mRepassword;
    private EditText mRephone;
    private RadioGroup mFlag;
    private RadioButton mBoss;
    private RadioButton mAdmin;
    private Button mregistered;
    String account, password, phone;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rergistered);
        mReaccount = (EditText) findViewById(R.id.reaccount);
        mRepassword = (EditText) findViewById(R.id.repassword);
        mRephone = (EditText) findViewById(R.id.rephone);
        mFlag = (RadioGroup) findViewById(R.id.flag);
        mBoss = (RadioButton) findViewById(R.id.boss);
        mAdmin = (RadioButton) findViewById(R.id.admin);
        mregistered = findViewById(R.id.registered);

        mFlag.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mBoss.getId()) {
                    flag = 2;
                } else {
                    flag = 1;
                }
            }
        });

        mregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = mReaccount.getText().toString();
                password = mRepassword.getText().toString();
                phone = mRephone.getText().toString();
                User user=new User();
                user.setUsername(account);
                user.setPassword(password);
                user.setMobilePhoneNumber(phone);
                user.setFlag(flag);
                user.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if(e==null){
                            Toast.makeText(Rergistered.this, "注册成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Log.d("TAG", "done: "+e.toString());
                        }
                    }
                });
            }
        });
    }
}
