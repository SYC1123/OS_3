package com.example.commoditymanagementsystem.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;
import com.example.commoditymanagementsystem.model.Bmob.User;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity {
    TextView registered, buyer, saleman;
    EditText account, password;
    Button Login;
    String account1, password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "5b425154964e46cd19279327ae06c390");
        registered = findViewById(R.id.registered);
        buyer = findViewById(R.id.buyer);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        account = findViewById(R.id.account);
        Login = findViewById(R.id.Login);
        saleman = findViewById(R.id.saleman);

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Rergistered.class);
                startActivity(intent);
            }
        });

        buyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemManager.class);
                intent.putExtra("type", "buyer");
                startActivity(intent);
            }
        });

        saleman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemManager.class);
                intent.putExtra("type", "saleman");
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account1 = account.getText().toString();
                password1 = password.getText().toString();
                User user = new User();
                user.setUsername(account1);
                user.setPassword(password1);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            User user1 = BmobUser.getCurrentUser(User.class);
                            Toast.makeText(MainActivity.this, "欢迎回来" + user1.getUsername(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ItemManager.class);
                            intent.putExtra("type", "");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
