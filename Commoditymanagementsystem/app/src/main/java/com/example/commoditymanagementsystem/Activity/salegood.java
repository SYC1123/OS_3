package com.example.commoditymanagementsystem.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;
import com.example.commoditymanagementsystem.model.Bmob.Statement;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class salegood extends AppCompatActivity {
    private TextView mGoodname;
    private EditText mGoodnum;
    private Button mSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salegood);
        bindViews();
        Intent intent=getIntent();
        final Good good= (Good) intent.getSerializableExtra("good");
        mGoodname.setText(good.getName());
        mSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoodnum.getText().toString().equals("")){
                    AlertDialog.Builder dia = new AlertDialog.Builder(salegood.this);
                    dia.setTitle("请输入内容！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                }else{
                    final int num=Integer.parseInt(mGoodnum.getText().toString());
                    if(num>good.getNum()){
                        AlertDialog.Builder dia = new AlertDialog.Builder(salegood.this);
                        dia.setTitle("商品不足！");
                        dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dia.show();
                    }else{
                        Good good1=new Good();
                        good1.setNum(good.getNum()-num);
                        good1.update(good.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                Statement statement=new Statement();
                                statement.setName(good.getName());
                                statement.setPrice(good.getUnit_price());
                                statement.setState("销售");
                                statement.setNum(num);
                                statement.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                            }
                        });
                        if(good.getNum()-num<=50){
                            AlertDialog.Builder dia = new AlertDialog.Builder(salegood.this);
                            dia.setTitle("商品不足50件，请补货！");
                            dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dia.show();
                        }
                        finish();
                    }
                }
            }
        });
    }
    private void bindViews() {
        mGoodname = (TextView) findViewById(R.id.goodname);
        mGoodnum = (EditText) findViewById(R.id.goodnum);
        mSale = (Button) findViewById(R.id.sale);
    }

}
