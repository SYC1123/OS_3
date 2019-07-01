package com.example.commoditymanagementsystem.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.commoditymanagementsystem.R;
import com.example.commoditymanagementsystem.model.Bmob.Good;
import com.example.commoditymanagementsystem.model.Bmob.Statement;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class newGood extends AppCompatActivity {
    private EditText mName;
    private EditText mCategory;
    private EditText mLocation;
    private EditText mNum;
    private EditText mPrice;
    private Button mChange;
    private Spinner spinner;
    private String category;

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
        spinner = findViewById(R.id.spinner2);
        BmobQuery<Good> bmobQuery = new BmobQuery<Good>();
        bmobQuery.addQueryKeys("category");
        bmobQuery.findObjects(new FindListener<Good>() {
            @Override
            public void done(List<Good> object, BmobException e) {
                if (e == null) {
                    // Toast.makeText(newGood.this, "查询成功：共" + object.size() + "条数据。", Toast.LENGTH_SHORT).show();
                    //注意：这里的Person对象中只有指定列的数据。
                    List<String> result = new ArrayList<String>(object.size());
                    for (Good str : object) {
                        if (!result.contains(str.getCategory())) {
                            result.add(str.getCategory());
                        }
                    }
                    //Toast.makeText(newGood.this, result.size()+"", Toast.LENGTH_SHORT).show();
                    ArrayAdapter adpter = new ArrayAdapter(newGood.this, R.layout.item, R.id.text, result);

                    spinner.setAdapter(adpter);

                    // spinner.setPrompt("ss");  //设置spinner展开后框体的title
                } else {
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(newGood.this, spinner.getSelectedItem()+"", Toast.LENGTH_SHORT).show();
                category = spinner.getSelectedItem() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Toast.makeText(newGood.this, spinner.getSelectedItem()+"", Toast.LENGTH_SHORT).show();
        mChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mName.getText().toString().equals("")
                        || mPrice.getText().toString().equals("")
                        || mLocation.getText().toString().equals("")
                        || mNum.getText().toString().equals("")) {
                    AlertDialog.Builder dia = new AlertDialog.Builder(newGood.this);
                    dia.setTitle("请输入内容！");
                    dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Toast.makeText(MainActivity.this, which+"确定", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dia.show();
                } else {
                    final Good good = new Good();
                    good.setName(mName.getText().toString());
                    good.setLocation(mLocation.getText().toString());

                    if (mCategory.getText().toString().equals("")) {
                        good.setCategory(category);
                    } else {
                        good.setCategory(mCategory.getText().toString());

                    }
                    good.setNum(Integer.parseInt(mNum.getText().toString()));
                    good.setUnit_price(Float.parseFloat(mPrice.getText().toString()));
                    good.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(newGood.this, "更新成功！", Toast.LENGTH_SHORT).show();
                                Statement statement = new Statement();
                                statement.setName(good.getName());
                                statement.setPrice(good.getUnit_price());
                                statement.setNum(good.getNum());
                                statement.setState("采购");
                                statement.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

}
