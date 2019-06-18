package com.example.broadcasttest;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button=findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("Your.action.name");
                intent.putExtra("data","传输的字符串");
                LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(Main2Activity.this);//管理广播，获取实例
                localBroadcastManager.sendBroadcast(intent);//发送广播
                finish();
            }
        });
    }
}
