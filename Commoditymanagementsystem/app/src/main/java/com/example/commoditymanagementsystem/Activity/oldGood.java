package com.example.commoditymanagementsystem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class oldGood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_good);
        Intent intent=getIntent();
        final String id=intent.getStringExtra("objectId");
        final int number=Integer.parseInt(intent.getStringExtra("num"));
        Button button=findViewById(R.id.sure);
        final EditText editTex=findViewById(R.id.number);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTex.getText().toString().equals("")) {
                    AlertDialog.Builder dia = new AlertDialog.Builder(oldGood.this);
                    dia.setTitle("请输入信息！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                }else {
                    Good p2 = new Good();
                    p2.setNum(Integer.parseInt(editTex.getText().toString())+number);
                    p2.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(oldGood.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            }else{

                            }
                        }

                    });
                }
            }
        });
    }
}
