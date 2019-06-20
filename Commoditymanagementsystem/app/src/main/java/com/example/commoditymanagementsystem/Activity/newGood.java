package com.example.commoditymanagementsystem.Activity;

import android.content.DialogInterface;
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
import cn.bmob.v3.listener.SaveListener;

public class newGood extends AppCompatActivity {
    private EditText mName;
    private EditText mCategory;
    private EditText mLocation;
    private EditText mNum;
    private EditText mPrice;
    private Button mChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_good);
        mName = (EditText) findViewById(R.id.name);
        mCategory = (EditText) findViewById(R.id.category);
        mLocation = (EditText) findViewById(R.id.location);
        mNum = (EditText) findViewById(R.id.number);
        mPrice = (EditText) findViewById(R.id.price);
        mChange = (Button) findViewById(R.id.change);
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().equals("")){
                    AlertDialog.Builder dia = new AlertDialog.Builder(newGood.this);
                    dia.setTitle("请输入内容！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                }else{
                    Good good = new Good();
                    good.setName(mName.getText().toString());
                    good.setLocation(mLocation.getText().toString());
                    good.setCategory(mCategory.getText().toString());
                    good.setNum(Integer.parseInt(mNum.getText().toString()));
                    good.setUnit_price(Float.parseFloat(mPrice.getText().toString()));
                    good.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(newGood.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
