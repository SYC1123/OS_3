package com.example.commoditymanagementsystem.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeMessage extends AppCompatActivity {
    private EditText mEditText;
    private Button mFind;
    private EditText mName;
    private EditText mCategory;
    private EditText mLocation;
    private EditText mNum;
    private EditText mPrice;
    private Button mChange;
    private Good good1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_message);
        bindViews();
        mFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditText.getText().toString().equals("")){
                    AlertDialog.Builder dia = new AlertDialog.Builder(ChangeMessage.this);
                    dia.setTitle("请输入内容！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                }else{
                    BmobQuery<Good> categoryBmobQuery = new BmobQuery<>();
                    categoryBmobQuery.addWhereEqualTo("name", mEditText.getText().toString());
                    categoryBmobQuery.findObjects(new FindListener<Good>() {
                        @Override
                        public void done(List<Good> object, BmobException e) {
                            if (e == null) {
                               good1=object.get(0);
                               mName.setText(good1.getName());
                               mCategory.setText(good1.getCategory());
                               mLocation.setText(good1.getLocation());
                               mNum.setText(good1.getNum()+"");
                               mPrice.setText(good1.getUnit_price()+"");
                            } else {
                                Toast.makeText(ChangeMessage.this, "该商品不存在！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().equals("")){
                    AlertDialog.Builder dia = new AlertDialog.Builder(ChangeMessage.this);
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
                    good.update(good1.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //Snackbar.make(mBtnUpdate, "更新成功", Snackbar.LENGTH_LONG).show();
                                Toast.makeText(ChangeMessage.this, "更新成功！", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("123", "done: "+e.toString());
                            }
                        }
                    });
                }
            }
        });
    }
    private void bindViews() {
        mEditText = (EditText) findViewById(R.id.findname);
        mFind = (Button) findViewById(R.id.find);
        mName = (EditText) findViewById(R.id.name);
        mCategory = (EditText) findViewById(R.id.category);
        mLocation = (EditText) findViewById(R.id.location);
        mNum = (EditText) findViewById(R.id.number);
        mPrice = (EditText) findViewById(R.id.price);
        mChange = (Button) findViewById(R.id.change);
    }

}
